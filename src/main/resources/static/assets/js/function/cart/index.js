TechVortex.service("CartService", function ($http, $rootScope) {
  this.calculateTotal = function (items) {
    return items.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0,
    );
  };
  // Thay đổi tên của hàm calculateTotal trong CartService
  this.calculateTotalCurrent = function (items) {
    return items.reduce((total, item) => total + (item.totalCurrent || 0), 0);
  };
});

TechVortex.controller(
  "cartController",
  function (
    $scope,
    $http,
    CartService,
    $rootScope,
    $timeout,
    $route,
    $window,
    $timeout,
    $location,
  ) {
    $scope.item = [];
    const token = localStorage.getItem("token");
    let listLocalStorage = JSON.parse(localStorage.getItem("Vortex")) || [];

    if (token) {
      const idUserName = JSON.parse(atob(localStorage.getItem("token")));

      $http
        .get("/findAllCart/" + idUserName.userName)
        .then((response) => {
          // tạo ra một mảng phần tử duy nhất
          let uniqueItems = {};
          response.data.forEach((item, index) => {
            if (!uniqueItems[index]) {
              uniqueItems[index] = {
                cartId: item.cartId,
                productDetailId: item.productDetails.productDetailId,
                product: item.productDetails.product,
                quantity: item.quantity,
                color: item.productDetails.color,
                brand: item.productDetails.brand,
                material: item.productDetails.material,
                quantityInventory: item.productDetails.inventoryQuantity,
              };
            } else {
              uniqueItems[index].quantity += item.quantity;
            }
          });

          //chuyển về mảng
          $scope.item = Object.values(uniqueItems);
          // Tính tổng sản phẩm trong cart
          $scope.totalCart = 0;
          $scope.countCart = response.data.length;

          $scope.item.map((item) => {
            $scope.totalCart += item.quantity * item.product.price;
          });

          $scope.minusQtyCart = function (id) {
            const quantity = document.getElementById(
              "input_quantity_" + id,
            ).value;
            const currentItem = $scope.item.find((item) => item.cartId == id);

            currentItem.totalCurrent = currentItem.product.price * quantity;
            $scope.result = 0;
            $http
              .get("/qty/" + id + "/" + quantity)
              .then((response) => {
                $http
                  .get("/findAllCart/" + idUserName.userName)
                  .then((response) => {
                    response.data.map((item) => {
                      $scope.result +=
                        item.quantity * item.productDetails.product.price;
                      $scope.totalCart = $scope.result;
                    });
                  });
              })
              .catch((error) => console.log(error));
          };

          $scope.plusQtyCart = function (id) {
            const quantity = document.getElementById(
              "input_quantity_" + id,
            ).value;
            const currentItem = $scope.item.find((item) => item.cartId == id);

            currentItem.totalCurrent = currentItem.product.price * quantity;
            $scope.result = 0;
            $http
              .get("/qty/" + id + "/" + quantity)
              .then((response) => {
                $http
                  .get("/findAllCart/" + idUserName.userName)
                  .then((response) => {
                    response.data.map((item) => {
                      $scope.result +=
                        item.quantity * item.productDetails.product.price;
                      $scope.totalCart = $scope.result;
                    });
                  });
              })
              .catch((error) => error);
          };

          //Tính tổng bằng số lượng
          $scope.totalQuantity = function (id, quantity, index) {
            const currentItem = $scope.item.find((item) => item.cartId == id);

            // kiem tra so luong
            if (
              quantity === null ||
              quantity === "" ||
              quantity === "0" ||
              quantity === undefined
            ) {
              $scope.deleteByIdCart(id);
              document.getElementById("input_quantity_" + id).value = 1;
              quantity = 1;
            }
            if (quantity > currentItem.quantityInventory) {
              document.getElementById("input_quantity_" + id).value =
                currentItem.quantityInventory;
              quantity = currentItem.quantityInventory;
              alertify
                .alert(
                  "<h6>Rất tiếc, bạn chỉ có thể mua tối đa " +
                    currentItem.quantityInventory +
                    " sản phẩm.</h6>",
                )
                .setHeader("");
            }
            // Tang san pham
            if (currentItem) {
              currentItem.totalCurrent = currentItem.product.price * quantity;
              //  trùng id thêm số lượng
              $http
                .get("/qty/" + id + "/" + quantity)
                .then((response) => {
                  $scope.result = 0;
                  $http
                    .get("/findAllCart/" + idUserName.userName)
                    .then((response) => {
                      response.data.map((item) => {
                        $scope.result +=
                          item.quantity * item.productDetails.product.price;
                        $scope.totalCart = $scope.result;
                      });
                    });
                })
                .catch((error) => error);
            }
            // Tạo đối tượng cart để lưu lại số lượng
            const cartItem = {
              cartId: currentItem.cartId,
              brand: {
                brandId: currentItem.brand.brandId,
                brandImage: currentItem.brand.brandImage,
                brandName: currentItem.brand.brandName,
              },
              color: {
                colorId: currentItem.color.colorId,
                colorName: currentItem.color.colorName,
              },
              material: {
                materialId: currentItem.material.materialId,
                materialName: currentItem.material.materialName,
              },
              product: {
                description: currentItem.product.description,
                image: currentItem.product.image,
                origin: currentItem.product.origin,
                price: currentItem.product.price,
                productId: currentItem.product.productId,
                productName: currentItem.product.productName,
              },
              quantity: quantity,
              totalCurrent: currentItem.totalCurrent,
            };

            // listLocalStorage.map((item) => {
            //   console.log(item);
            // });
            listLocalStorage.splice(index, 1);
            localStorage.setItem("Vortex", JSON.stringify(listLocalStorage));

            listLocalStorage.push(cartItem);
            localStorage.setItem("Vortex", JSON.stringify(listLocalStorage));
          };

          $scope.sum = 0;

          //Tính tổng đơn hàng và check được chọn lưu vào localStorage
          $scope.inputOrder = function (id) {
            const currentItem = $scope.item.find((x) => x.cartId == id);

            // Kiểm tra nếu không tìm thấy sản phẩm
            if (!currentItem) return;

            const quantity = document.getElementById(
              "input_quantity_" + id,
            ).value;

            const checkBox = document.getElementById("buy_click_" + id);

            // điếm số lượng chính nó
            let countChecked = document.querySelectorAll(
              ".input-product:checked",
            ).length;

            // Nếu checkbox được chọn
            if (checkBox.checked) {
              // Thêm giá sản phẩm vào tổng
              $scope.sum += currentItem.product.price * quantity;

              //hiển thị nó ra class count-product
              document.querySelectorAll(".count-product").forEach((item) => {
                item.innerHTML = countChecked;
              });

              // Kiem tra xem co ton tai hay khong
              listLocalStorage.map((x) => {
                if (x.cartId === currentItem.cartId) {
                  let index = listLocalStorage.findIndex(
                    (x) => x.cartId === currentItem.cartId,
                  );
                  if (index !== -1) {
                    listLocalStorage.splice(index, 1),
                      localStorage.setItem(
                        "Vortex",
                        JSON.stringify(listLocalStorage),
                      );
                  }
                }
              });

              if (listLocalStorage.length === 0) {
                console.log(currentItem);
              }
              listLocalStorage.push(currentItem);
              localStorage.setItem("Vortex", JSON.stringify(listLocalStorage));
            } else {
              // Nếu checkbox bị hủy chọn, trừ đi giá sản phẩm khỏi tổng
              $scope.sum -= currentItem.product.price * quantity;

              // không check xóa khỏi list
              let index = listLocalStorage.findIndex(
                (x) => x.cartId === currentItem.cartId,
              );

              //hiển thị nó ra class count-product
              document.querySelectorAll(".count-product").forEach((item) => {
                item.innerHTML -= 1;
              });

              //  Xóa phần tử khỏi mảng
              listLocalStorage.splice(index, 1);

              // Cập nhật localStorage
              localStorage.setItem("Vortex", JSON.stringify(listLocalStorage));
            }
          };

          $rootScope.$on(
            "$locationChangeStart",
            function (event, next, current) {
              if ($location.path() !== "/checkout") {
                localStorage.removeItem("Vorktex");
              }
            },
          );

          // delete carts by id
          $scope.deleteByIdCart = function (id, index) {
            const currentItem = $scope.item.find((x) => x.cartId == id);
            const quantity = currentItem.quantity;
            const checkBox = document.getElementById(id);
            alertify
              .confirm()
              .set("movable", false)
              .set("labels", { ok: "Ok", cancel: "Hủy" })
              .setHeader("")
              .set({
                transition: "fade",
                message: "<h6>Bạn chắc chắn muốn xóa sản phẩm này</h6>",
                onok: function () {
                  $http
                    .get(`/deleteItemByIdFromCart/` + id)
                    .then((response) => {
                      // Gọi lại số lượng cart
                      $http
                        .get(`/countcart/` + idUserName.userName)
                        .then((response) => {
                          $rootScope.qtyItem = response.data;
                        })
                        .catch((error) => console.log(error));

                      // gọi lại tổng
                      $scope.result = 0;
                      $http
                        .get("/findAllCart/" + idUserName.userName)
                        .then((response) => {
                          response.data.map((item) => {
                            console.log(item);
                            $scope.result +=
                              item.quantity * item.productDetails.product.price;
                            $scope.totalCart = $scope.result;
                          });
                          // Gọi lại cart
                          $scope.countCart = response.data.length;
                        });

                      const index = listLocalStorage.find(
                        (x) => x.cartId == id,
                      );
                      listLocalStorage.splice(index, 1);
                      localStorage.setItem(
                        "Vortex",
                        JSON.stringify(listLocalStorage),
                      );
                      // Cập nhật lại giao diện
                      $scope.item.splice(index, 1);
                      if (checkBox == null) {
                      } else {
                        if (checkBox.checked) {
                          $scope.sum -= currentItem.product.price * quantity;
                        }
                      }
                    })
                    .catch((error) => console.log(error));
                },
              })
              .show();
          };

          // all delete carts
          $scope.removeAll = function () {
            alertify
              .confirm()
              .set("movable", false)
              .set("labels", { ok: "Ok", cancel: "Hủy" })
              .setHeader("")
              .set({
                transition: "fade",
                message:
                  "<h6>Bạn chắc chắn muốn xóa tất sản phẩm trong giỏ hàng</h6>",
                onok: function () {
                  $http
                    .get(`/deleteAllById/` + idUserName.userName)
                    .then((response) => {
                      // Gọi lại số lượng cart
                      $http
                        .get(`/countcart/` + idUserName.userName)
                        .then((response) => {
                          $rootScope.qtyItem = response.data;
                        })
                        .catch((error) => console.log(error));

                      localStorage.removeItem(
                        "Vortex",
                        JSON.stringify(listLocalStorage),
                      );
                      // Cập nhật lại giao diện
                      $scope.item.splice(0);
                      // $scope.$apply();
                    })
                    .catch((error) =>
                      console.error(
                        "Error occurred while deleting item:",
                        error,
                      ),
                    );
                },
              })
              .show();
          };
          $scope.checkoutAccessDefined = function () {
            const define = angular.element(
              document.querySelectorAll(".mytime:checked"),
            );
            // if (listLocalStorage.length === 0) {
            //   event.preventDefault();
            //   alertify
            //     .alert()
            //     .set("movable", false)
            //     .set("label", "Ok")
            //     .setHeader("")
            //     .set({
            //       transition: "fade",
            //       message: "<h6>Chọn ít nhất một sản phẩm</h6>",
            //     })
            //     .show();
            // } else {
            $location.path("/checkout");
            // }
          };
        })
        .catch((error) => console.error(error));
    }
  },
);
