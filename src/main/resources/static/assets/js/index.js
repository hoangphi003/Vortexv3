const TechVortex = angular.module("TechVortex", ["ngRoute"]);

TechVortex.controller(
  "HomeController",
  function (
    $scope,
    $rootScope,
    $location,
    $window,
    $http,
    $route,
    DetailService,
  ) {
    // Các thông báo

    // Thông báo thành công
    $scope.alertMessageSuccess = function (message) {
      alertify.set("notifier", "position", "top-center").set("delay", 1.5);
      alertify.message('<i class="fa-solid fa-circle-check"></i>' + message);
      $route.reload();
    };

    $scope.alertCartSuccess = function () {
      alertify.set("notifier", "position", "top-center").set("delay", 1.5);
      alertify.message(
        '<i class="fa-solid fa-circle-check"></i>Sản phẩm đã được thêm vào giỏ hàng',
      );
    };

    // Thông báo mặc định
    $scope.alertMessageDefault = function (message) {
      alertify.set("notifier", "position", "top-center").set("delay", 0.9);
      alertify.message(message);
    };

    $scope.notifierSuccess = function (message) {
      alertify.set("notifier", "position", "top-right");
      alertify.success(message);
    };

    $scope.notifierDefault = function (message) {
      alertify.set("notifier", "position", "top-right");
      alertify.message(message);
    };

    $scope.notifierFail = function (message) {
      alertify.set("notifier", "position", "top-right");
      alertify.error(message);
    };

    // Kiểm tra đã đăng nhập
    $scope.getUser = function () {
      return localStorage.getItem("token") !== null;
    };

    // Đăng xuất
    $scope.logoff = function () {
      localStorage.removeItem("token");
      localStorage.removeItem("compare");
      localStorage.removeItem("TechVortexCart");
      $route.reload();
    };
    $scope.qtyDetail = 1;

    // Lấy số lượng giỏ hàng
    const idUser = localStorage.getItem("token");
    if (idUser) {
      const Info = JSON.parse(atob(idUser));

      $http
        .get(`/countcart/` + Info.userName)
        .then((Response) => {
          $rootScope.qtyItem = Response.data;
        })
        .catch((error) => console.log(error));

      // Add to cart
      $scope.addToCart = function (id) {
        // Get user
        if (idUser) {
          $scope.cart = {
            account: {
              userName: Info.userName,
            },
            productDetails: {
              productDetailId: id,
            },
            quantity: $scope.qtyDetail,
          };

          $http
            .post("/addtocart/", $scope.cart, {
              headers: { "Content-Type": "application/json" },
            })
            .then((Response) => {
              $http
                .get(`/countcart/` + Info.userName)
                .then((response) => {
                  $rootScope.qtyItem = response.data;
                })
                .catch((error) => console.log(error));

              $scope.alertCartSuccess();
            })
            .catch((error) => {});
        } else {
          $location.path("/login");
        }
      };
    }

    $http
      .get("/findAllBrand")
      .then((Response) => {
        $scope.brands = Response.data;
      })
      .catch((error) => console.log(error));

    $http
      .get("/findAllCategory")
      .then((Response) => {
        $scope.categories = Response.data;
      })
      .catch((error) => console.log(error));

    $http
      .get("/findAllProducts")
      .then((Response) => {
        $scope.products = Response.data;

        // Tính trung bình sao
        $scope.calculateAverageRating = function (reviews) {
          var total = 0;
          for (var i = 0; i < reviews.length; i++) {
            total += reviews[i].rating;
          }

          return Math.floor(total / reviews.length);
        };

        // Điếm lượt đánh giá
        $scope.countAverageRating = function (reviews) {
          return reviews.length;
        };
      })
      .catch((error) => console.log(error));

    $scope.buyDetail = function (id) {
      $location.path("#!/detail/" + id);
    };

    $rootScope.$on("$locationChangeStart", function (event) {
      window.scrollTo(0, 0);
    });

    $http.get("/findAllProductsDesc").then((Response) => {
      $scope.productDesc = Response.data;
    });

    $http.get("/productHot").then((Response) => {
      $scope.results = [];
      $scope.productHot = Response.data;
      $scope.productHot.map((order) => {
        order.orderDetails.map((orderDetails) => {
          $http.get("/findAllProducts").then((Response) => {
            $scope.productDetail = Response.data;
            $scope.productDetail.find((hot) => {
              if (hot.product.productId === orderDetails.product.productId) {
                $scope.results.push(hot);
              }
            });
          });
        });
      });
    });
  },
);
