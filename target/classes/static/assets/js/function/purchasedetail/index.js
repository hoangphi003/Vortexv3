TechVortex.controller(
  "PurchaseOrderController",
  function ($scope, $http, $routeParams, $route, $rootScope, $location) {
    $rootScope.productInfo;

    const userToken = localStorage.getItem("token");
    const userID = JSON.parse(atob(userToken));

    if (userToken) {
      $scope.purchaseDetail = function (id) {
        $http
          .get("/findOrderDetail/" + userID.userName + "/" + id)
          .then((Response) => {
            let total = 0;
            $rootScope.data = Response.data;
            Response.data.orderDetails.map((item) => {
              $rootScope.productInfo = item;
              total += item.total;
            });
            $rootScope.total = total;
          })
          .catch((error) => console.log(error));
        $location.path("/purchasedetail");
      };

      $scope.getOrderId = function (id) {
        $rootScope.orderId = id;
      };

      $scope.submitCancelOrder = function () {
        const form = {
          reason: $scope.reason,
        };

        const jsonData = JSON.stringify(form);

        if (validateReason()) {
          $("#cancelOrder").modal("hide");
          clear();
          $http
            .get(`/cancelorder/` + $rootScope.orderId, {
              headers: { "Content-Type": "application/json" },
            })
            .then((Response) => {
              $scope.purchaseDetail($rootScope.orderId);
              $http
                .post(`/sendmailcancelorder/` + $rootScope.orderId, jsonData, {
                  headers: { "Content-Type": "application/json" },
                })
                .then((Response) => {});
            })
            .catch((error) => console.log(error + "lỗi tại cancelOrderByUser"));
        }
      };

      $scope.getordered = function (id) {
        alertify
          .confirm()
          .set("movable", false)
          .set("labels", { ok: "Ok", cancel: "Hủy" })
          .setHeader("")
          .set({
            transition: "fade",
            message: "<h5>Bạn đã nhận được đơn hàng này</h5>",
            onok: function () {
              $http
                .get(`/getordered/` + id)
                .then((Response) => {
                  $scope.purchaseDetail(id);
                })
                .catch((error) => console.log(error + "lỗi tại getordered js"));
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
