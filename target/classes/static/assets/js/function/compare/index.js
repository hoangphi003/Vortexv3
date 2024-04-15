TechVortex.controller(
  "CompareController",
  function ($scope, $rootScope, $location, $window, $http, $route) {
    $scope.list = [];
    $scope.viewCompare = function (id) {
      $http.get("/detail/" + id).then((Response) => {
        const findId = $scope.list.find((x) => id === x.productDetailId);
        if (findId != null) {
          return;
        } else {
          $scope.list.push(Response.data);
        }
      });
    };
    $scope.deleteCompare = function (index) {
      $scope.list.splice(index, 1);
    };

    $scope.compareNow = function () {
      if ($scope.list.length < 2) {
        $scope.messages = "Tối thiểu có 2 sản phẩm để so sánh";
      } else {
        $location.path("/compare");
        $scope.messages = "";
        localStorage.setItem("compare", JSON.stringify($scope.list));
      }
    };

    const product = localStorage.getItem("compare");
    $scope.listCompare = JSON.parse(product);

    $scope.deleteByEachProductProduct = function (index) {
      $scope.listCompare.splice(index, 1);
      localStorage.setItem("compare", JSON.stringify($scope.listCompare));
    };
  },
);
