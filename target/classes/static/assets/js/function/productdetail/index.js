TechVortex.controller(
  "ProductDetailController",
  function (
    $http,
    $scope,
    $routeParams,
    $location,
    $route,
    $window,
    $rootScope,
    DetailService,
    $timeout,
  ) {
    $scope.qtyDetail = 1;
    $scope.inventory = {};

    $http
      .get("/detail/" + $routeParams.id)
      .then((Response) => {
        $scope.detail = Response.data;

        // Điếm toàn bộ đánh giá theo mã sản phẩm
        DetailService.getCountReview(
          $scope.detail.product.productId,
          function (data) {
            $scope.counter = data;
          },
        );

        //điểm yêu thích
        DetailService.countFavoriteProduct(
          Response.data.product.productId,
          function (data) {
            $scope.counterFavorite = data;
          },
        );

        // Tính trung bình sao
        DetailService.getAvgReview(
          $scope.detail.product.productId,
          function (data) {
            $scope.avgreview = data;
          },
        );

        // Lấy sản phẩm lơn hơn 4 sao
        DetailService.getCountReviewThen4s(
          $scope.detail.product.productId,
          function (data) {
            $scope.than4s = data;
          },
        );

        $scope.chooseStart = function (start) {
          DetailService.filterStartByUser(
            $scope.detail.product.productId,
            start,
            function (data) {
              if (data) {
                $scope.startsData = data;
              } else {
                $scope.startsData;
              }
            },
          );
        };

        // Lấy sản phẩm cùng loại
        let categoryId = Response.data.product.category.categoryId;
        $http
          .get("/findAllProductsByCategory/" + categoryId)
          .then((Response) => {
            $scope.categoryData = Response.data;
          })
          .catch((error) => error);
      })
      .catch((error) => error);

    $scope.qtyDetail;
    $scope.addToCart = function (id) {
      // Get user
      const tokenUser = localStorage.getItem("token");
      if (tokenUser) {
        const userInfo = JSON.parse(atob(tokenUser));
        $scope.cart = {
          account: {
            userName: userInfo.userName,
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
              .get(`/countcart/` + userInfo.userName)
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

    $scope.buyNow = function (id) {
      $scope.addToCart(id);
      $location.path("/cart");
      $route.reload();
    };

    $scope.validateQty = function (quantity) {
      if (quantity > $scope.detail.inventoryQuantity) {
        $scope.qtyDetail = $scope.detail.inventoryQuantity;
        $scope.notifierFail("Đã vượt số lượng trong kho");
      }
    };

    $scope.findByCategory = function (categoryId) {
      $rootScope.categoryData = {};
    };
  },
);
