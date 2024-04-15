TechVortex.controller(
  "FavoriteController",
  function ($scope, $rootScope, $http, $route, $routeParams, $location) {
    const tokenUser = localStorage.getItem("token");
    if (tokenUser) {
      const userID = JSON.parse(atob(tokenUser));
      $http
        .get("/findallfavorite/" + userID.userName)
        .then((Response) => {
          $scope.favorites = Response.data;
        })
        .catch((error) => console.log(error));
    }
    $scope.deleteFavorite = function (id) {
      const index = $scope.favorites.findIndex(
        (item) => item.wishListId === id,
      );
      $http
        .get("/deletefavorite/" + id)
        .then((Response) => {
          $scope.favorites.splice(index, 1);
        })
        .catch((error) => console.log(error));
    };

    $scope.addFavorite = function (id) {
      if (tokenUser) {
        const userID = JSON.parse(atob(tokenUser));
        $scope.favorite = {
          account: { userName: userID.userName },
          date: new Date(),
          productDetails: {
            productDetailId: id,
          },
        };
        $http
          .post("/addfavorite/" + id + "/" + userID.userName, $scope.favorite, {
            headers: { "Content-Type": "application/json" },
          })
          .then((Response) => {
            if (Response.data) {
              $scope.notifierFail("Sản phẩm này đã có sẵn ");
            } else {
              $scope.notifierSuccess(
                "Đã thêm sản phẩm yêu thích <br> xem <a href='#!favorite' class='text-white text-decoration-underline'> tại đây</a>",
              );
            }
          })
          .catch((error) => console.log(error));
      } else {
        $location.path("/login");
      }
    };
  },
);
