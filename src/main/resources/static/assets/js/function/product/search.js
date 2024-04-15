TechVortex.service("ProductService", function ($http) {
  var apiUrl = "/findAll";
  return {
    getYourEntities: function (page, size) {
      return $http.get(apiUrl, {
        params: { page: page, size: size },
      });
    },
  };
});
TechVortex.controller(
  "ProductsSearch",
  function ($scope, $http, $rootScope, ProductService) {
    $scope.loadEntities = function () {
      ProductService.getYourEntities(
        $scope.page,
        $scope.size,
        $scope.pageCount,
      ).then(function (response) {
        $scope.products = response.data.content;
        $scope.totalPages = response.data.totalPages;
      });
    };
    $scope.loadMore = function () {
      $scope.size++;
      $scope.loadEntities();
    };

    $scope.keyword = ""; // Initialize keyword
    $scope.searchKeyword = function () {
      $http
        .get("/user/productsearch/" + $scope.keyword)
        .then(function (Response) {
          $rootScope.items = Response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    };
  },
);
