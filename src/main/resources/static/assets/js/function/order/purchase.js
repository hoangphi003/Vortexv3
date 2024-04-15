TechVortex.controller(
  "PurchaseController",
  function ($scope, $http, $location, $route, $rootScope) {
    const userToken = localStorage.getItem("token");
    if (userToken) {
      const userID = JSON.parse(atob(userToken));
      $scope.ordersStand = [];
      $scope.totalAmount = 0;
      $scope.reason = "";

      $http
        .get(`/vieworder/` + userID.userName)
        .then((Response) => {
          $scope.orders = Response.data;
          $scope.orders.map((item) => {
            $http
              .get("/findOrderDetail/" + userID.userName + "/" + item.orderId)
              .then((Response) => {
                $scope.ordersStand.push(Response.data);
              })
              .catch((error) => error);
          });
        })
        .catch((error) => console.log(error + "lỗi tại bạn"));

      // Trạng thái đang chờ xác nhận
      $scope.awaiting = function () {
        $http
          .get(`/viewawaitingpayment/` + userID.userName)
          .then((Response) => {
            $scope.ordersAwait = Response.data;
            console.log(Response.data);
          })
          .catch((error) => console.log(error + "lỗi tại awaitingPayment"));
      };

      //Trạng thái chờ giao hàng
      $scope.delivery = function () {
        $http
          .get(`/waitingfordelivery/` + userID.userName)
          .then((Response) => {
            $scope.deliverys = Response.data;
          })
          .catch((error) => console.log(error + "lỗi tại delivery"));
      };

      $scope.inTransit = function () {
        $http
          .get(`/viewawaitingconfirm/` + userID.userName)
          .then((Response) => {
            $scope.inTransits = Response.data;
          })
          .catch((error) => console.log(error + "lỗi tại confirm"));
      };

      //Trạng thái hoàn thành
      $scope.complete = function () {
        $http
          .get(`/complete/` + userID.userName)
          .then((Response) => {
            $scope.completes = Response.data;
          })
          .catch((error) => console.log(error + "lỗi tại complete"));
      };

      //Trạng thái gọi hủy đơn
      $scope.cancel = function () {
        $http
          .get(`/cancel/` + userID.userName)
          .then((Response) => {
            $scope.cancel = Response.data;
          })
          .catch((error) => console.log(error + "lỗi tại cancel"));
      };

      $scope.getOrderId = function (id) {
        $rootScope.orderId = id;
      };

      $scope.submitCancelOrder = function () {
        const form = {
          reason: $scope.reason,
        };
        console.log(form);
        const jsonData = JSON.stringify(form);

        if (validateReason()) {
          $("#cancelOrder").modal("hide");
          clear();
          $http
            .get(`/cancelorder/` + $rootScope.orderId, {
              headers: { "Content-Type": "application/json" },
            })
            .then((Response) => {
              $route.reload();
              $http
                .post(`/sendmailcancelorder/` + $rootScope.orderId, jsonData, {
                  headers: { "Content-Type": "application/json" },
                })
                .then((Response) => {});
            })
            .catch((error) => console.log(error + "lỗi tại cancelOrderByUser"));
        }
      };
      // alertify
      //   .confirm()
      //   .set("movable", false)
      //   .set("labels", { ok: "Ok", cancel: "Hủy" })
      //   .setHeader("")
      //   .set({
      //     transition: "fade",
      //     message: "<h5>Bạn chắc chắn muốn hủy đơn hàng này</h5>",
      //     onok: function () {
      //       $http
      //         .get(`/cancelorder/` + id)
      //         .then((Response) => {
      //           $route.reload();
      //         })
      //         .catch((error) =>
      //           console.log(error + "lỗi tại cancelOrderByUser"),
      //         );
      //     },
      //   })
      //   .show();

      $scope.getOrderSuccessByUser = function (id) {
        alertify
          .confirm()
          .set("movable", false)
          .set("labels", { ok: "Ok", cancel: "Hủy" })
          .setHeader("")
          .set({
            transition: "fade",
            message: "<h5>Bạn chắc chắn đã nhận đơn hàng này</h5>",
            onok: function () {
              $http
                .get(`/getordersuccess/` + id)
                .then((Response) => {
                  $route.reload();
                })
                .catch((error) =>
                  console.log(error + "lỗi tại getOrderSuccessByUser"),
                );
            },
          })
          .show();
      };

      $scope.getOrdered = function (id) {
        alertify
          .confirm()
          .set("movable", false)
          .set("labels", { ok: "Ok", cancel: "Hủy" })
          .setHeader("")
          .set({
            transition: "fade",
            message: "<h5>Bạn đã nhận đơn hàng này</h5>",
            onok: function () {
              $http
                .get(`/getordered/` + id)
                .then((Response) => {
                  $http
                    .get(`/updateinventory/` + id)
                    .then((Response) => {
                      $route.reload();
                    })
                    .catch((error) =>
                      console.log(error + "lỗi tại updateinventory"),
                    );
                })
                .catch((error) => console.log(error + "lỗi tại getOrdered"));
            },
          })
          .show();
      };

      function validateReason() {
        if ($scope.reason) {
          $scope.message = "";
          $scope.messageBorder = "";
        } else {
          $scope.message = "Lý do không hợp lệ";
          $scope.messageBorder = "red-border";
          return;
        }
        return true;
      }

      function clear() {
        $scope.reason = "";
      }
    }
  },
);
