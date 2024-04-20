TechVortex.controller(
  "CheckoutController",
  function ($scope, $rootScope, $route, $http, $location, $window) {
    $scope.fullName = "";
    $scope.phoneNumber = "";
    $scope.address = "";
    $scope.province = "";
    $scope.district = "";
    $scope.ward = "";
    $scope.paymentmethod = "";

    const token = localStorage.getItem("token");
    $scope.sum = 0;

    $scope.orderDetails = [];
    $scope.discountId = null;

    // apis provider and wards
    $http
      .get(
        "https://vnprovinces.pythonanywhere.com/api/provinces/?basic=true&limit=100",
      )
      .then((Response) => {
        $scope.providers = Response.data.results;
      })
      .catch((error) => console.log(error, "loi provider"));

    $scope.getProviderId = function () {
      $http
        .get(
          `https://vnprovinces.pythonanywhere.com/api/provinces/` +
            $scope.province.id,
        )
        .then((Response) => {
          $scope.districts = Response.data.districts;
        })
        .catch((error) => console.log(error, "loi get providerid"));
      // thêm class d-none ẩn option mặc định
    };

    $scope.getWard = function () {
      if ($scope.district != null) {
        $http
          .get(
            `https://vnprovinces.pythonanywhere.com/api/districts/` +
              $scope.district.id,
          )
          .then((Response) => {
            $scope.wards = Response.data.wards;
          })
          .catch((error) => console.log(error, "loi get getWard"));
      }
    };

    if (token) {
      const idUserName = JSON.parse(atob(localStorage.getItem("token")));

      $http
        .get("/findAllCart/" + idUserName.userName)
        .then((response) => {
          $scope.listCart = response.data;

          $scope.listCart.map((x) => {
            $scope.sum += x.productDetails.product.price * x.quantity;
            $scope.orderDetail = {
              price: x.productDetails.product.price,
              quantity: x.quantity,
              product: {
                productId: x.productDetails.product.productId,
              },
              total: x.productDetails.product.price * x.quantity,
            };
            $scope.orderDetails.push($scope.orderDetail);
          });

          $scope.total =
            $scope.total !== undefined && $scope.total !== 0
              ? $scope.total
              : $scope.sum;

          $scope.enterDiscountCode = function () {
            $http
              .get(`getdiscount/` + $scope.discountModel)
              .then((Response) => {
                if (Response.data) {
                  if (Response.data.quantity > 1) {
                    if (new Date(Response.data.endDay) > new Date()) {
                      $scope.sumAfterDiscount =
                        (Response.data.percents / 100) * $scope.sum;
                      $scope.total = $scope.sum - $scope.sumAfterDiscount;
                      $scope.discountId = Response.data.discountId;

                      $http.get("/setqtydiscount/" + $scope.discountId);
                    } else {
                      $scope.notifierFail("Mã này đã hết hạn sử dụng");
                    }
                  } else {
                    $scope.notifierFail("Số lượng cho mã này đã hết");
                  }
                } else {
                  $scope.notifierFail("Mã giảm giá không hợp lệ");
                }
              })
              .catch((error) => console.log(error));
          };
          $scope.placeAnOrder = function () {
            $scope.order = {
              address:
                $scope.address +
                ", " +
                $scope.ward?.full_name +
                ", " +
                $scope.district?.full_name +
                ", " +
                $scope.province?.full_name,
              orderDate: new Date(),
              orderStatus: "Chờ xác nhận",
              account: {
                userName: idUserName.userName,
              },
              orderDetails: $scope.orderDetails,
              paymentMethod: $scope.paymentmethod,
              discount: $scope.discountId,
            };
            if (validate()) {
              if ($scope.paymentmethod === "vnPay") {
                $http
                  .get("/create_payment/" + $scope.total)
                  .then((Response) => {
                    location.href = Response.data.url;

                    localStorage.setItem(
                      "checkoutVortex",
                      JSON.stringify($scope.order),
                    );
                  })
                  .catch((error) => console.error("Error:", error));
              }

              if ($scope.paymentmethod === "payPal") {
                let number = $scope.total / 25000;
                location.href = "/paypal/" + parseFloat(number.toFixed(1));

                localStorage.setItem(
                  "checkoutVortex",
                  JSON.stringify($scope.order),
                );
              }

              if ($scope.paymentmethod === "cashPay") {
                if ($scope.discountId) {
                  $http
                    .post(
                      `/addorderdiscount/` + $scope.discountId,
                      $scope.order,
                      {
                        headers: { "Content-Type": "application/json" },
                      },
                    )
                    .then((Response) => {})
                    .catch((error) => console.log(error));
                } else {
                  $http
                    .post(`/addorder/`, $scope.order, {
                      headers: { "Content-Type": "application/json" },
                    })
                    .then((Response) => {})
                    .catch((error) => console.log(error));
                }
              }
            }
          };
        })
        .catch((error) => error);
    }

    const errorPhone = document.getElementById("errorPhone");
    const phone = document.getElementById("phone");

    phone.addEventListener("input", function () {
      const inputValue = phone.value; // Lấy giá trị và loại bỏ khoảng trắng ở đầu và cuối
      if (
        inputValue.length !== 10 ||
        inputValue.charAt(0) !== "0" ||
        !/^\d+$/.test(inputValue)
      ) {
        phone.style.border = "1px solid red";
        errorPhone.classList.remove("d-none");
        return;
      } else {
        phone.style.border = "1px solid #3b5d50";
        errorPhone.classList.add("d-none");
      }
    });

    function validate() {
      if ($scope.fullName === "") {
        $scope.messageFullName = "Nhập tên của bạn";
        $scope.messageFullNameBorder = "red-border";
        return false;
      } else {
        $scope.messageFullName = "";
        $scope.messageFullNameBorder = "";
      }
      if ($scope.phoneNumber === "") {
        $scope.messagePhoneNumber = "Nhập số điện thoại của bạn";
        $scope.messagePhoneNumberBorder = "red-border";
        return false;
      } else {
        $scope.messagePhoneNumber = "";
        $scope.messagePhoneNumberBorder = "";
      }
      if ($scope.address === "") {
        $scope.messageAddress = "Nhập địa chỉ của bạn";
        $scope.messageAddressBorder = "red-border";
        return false;
      } else {
        $scope.messageAddress = "";
        $scope.messageAddressBorder = "";
      }
      if ($scope.province === "Tỉnh/Thành Phố") {
        $scope.messageProvince = "Nhập tỉnh/thành phố của bạn";
        $scope.messageProvinceBorder = "red-border";
        return false;
      } else {
        $scope.messageProvince = "";
        $scope.messageProvinceBorder = "";
      }
      if ($scope.district === "Quận/Huyện") {
        $scope.messageDistrict = "Nhập quận/huyện phố của bạn";
        $scope.messageDistrictBorder = "red-border";
        return false;
      } else {
        $scope.messageDistrict = "";
        $scope.messageDistrictBorder = "";
      }
      if ($scope.ward === "Phường/Xã") {
        $scope.messageWard = "Nhập phường/xã phố của bạn";
        $scope.messageWardBorder = "red-border";
        return false;
      } else {
        $scope.messageWard = "";
        $scope.messageWardBorder = "";
      }
      if ($scope.paymentmethod === "") {
        $scope.messagePaymentmethod = "Chọn phương thức thanh toán";
        $scope.messagePaymentmethodBorder = "red-border";
        return false;
      } else {
        $scope.messagePaymentmethod = "";
        $scope.messagePaymentmethodBorder = "";
      }

      return true;
    }
  },
);
