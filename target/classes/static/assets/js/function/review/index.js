TechVortex.controller(
  "ReviewProductController",
  function ($scope, $rootScope, $http, $location, $route, $document) {
    $scope.comment = "";
    $rootScope.ratingStart;
    $rootScope.productInfo;
    $rootScope.images = [];
    const user = localStorage.getItem("token");
    $scope.viewReview = function (id) {
      $http
        .get("/findorderid/" + id)
        .then((Response) => {
          $rootScope.data = Response.data;
          Response.data.orderDetails.map((item) => {
            $rootScope.productInfo = item;
          });
        })
        .catch((error) => console.log(error));
    };
    $scope.rating = function (num) {
      $rootScope.ratingStart = num;
      if (num === 1) {
        $scope.message = "Kém";
        $scope.checked1 = "checked";
      } else if (num === 2) {
        $scope.message = "Tạm ổn";
        $scope.checked2 = "checked";
      } else if (num === 3) {
        $scope.message = "Tốt";
        $scope.checked3 = "checked";
      } else if (num === 4) {
        $scope.message = "Quá tốt";
        $scope.checked4 = "checked";
      } else if (num === 5) {
        $scope.message = "Tuyệt vời";
        $scope.checked5 = "checked";
      }
    };

    $scope.saveImages = function (files) {
      if (files.length >= 6) {
        $scope.notifierFail("Bạn chỉ được thêm tối đa được 5 hình");
        return;
      }
      for (var i = 0; i < files.length; i++) {
        $rootScope.images.push({
          image: files[i].name,
        });
        $scope.$apply();
      }
    };
    $scope.completeReview = function () {
      if (validate()) {
        if (user) {
          const userAtob = JSON.parse(atob(user));
          $scope.review = {
            comment: $scope.comment,
            rating: $rootScope.ratingStart,
            date: new Date(),
            account: {
              userName: userAtob.userName,
            },
            product: {
              productId: $rootScope.productInfo.product.productId,
            },
            reviewImgs: [$rootScope.images][0],
          };
          $http
            .post("/postreview", $scope.review, {
              headers: { "Content-Type": "application/json" },
            })
            .then((Response) => {
              $(".modal").modal("hide");
              $scope.notifierSuccess("Cám ơn bạn đã đánh giá");
              $http
                .get(`/getordersuccess/` + $rootScope.data.orderId)
                .then((Response) => {
                  $route.reload();
                })
                .catch((error) => console.log(error + "lỗi tại complete"));
            })
            .catch((error) => console.log(error));
        }
        $scope.clearForm();
      }
    };

    function validate() {
      if ($scope.comment === "") {
        $scope.messageComment = "Cho chúng tôi biết lý do bạn thích sản phẩm";
        $scope.commentBorder = "red-border";
        return false;
      } else {
        $scope.messageComment = "";
        $scope.commentBorder = "";
      }
      if ($rootScope.ratingStart === undefined) {
        $scope.message = "Vui lòng chọn sao";
        return false;
      }
      return true;
    }

    $scope.clearForm = function () {
      $scope.comment = "";
      $scope.message = "";
      $scope.messageComment = "";
      $scope.commentBorder = "";
      $rootScope.ratingStart = 0;
      $scope.resetImg();
    };

    $scope.resetImg = function () {
      $rootScope.images = [];
      angular.element(document.querySelector("#fileInput")).val(null);
    };
  },
);
