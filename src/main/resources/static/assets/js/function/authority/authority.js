var app = angular.module("authority", []);
app.controller("authorityController", function ($scope, $http, $location) {
  $scope.roles = [];
  $scope.admins = [];
  $scope.authorities = [];
  $scope.initialize = function () {
    // load tat ca roles
    $http
      .get("/rest/roles")
      .then((resp) => {
        $scope.roles = resp.data;
      })
      .catch((err) => {
        console.log("Error occurred while fetching roles:", err);
      });
  };

  // load staff and customers
  $http.get("/rest/accounts?admin=true").then((resp) => {
    $scope.admins = resp.data;
  });

  // load authorities of staff and customers
  $http
    .get("/rest/authorities?admin=true")
    .then((resp) => {
      $scope.authorities = resp.data;
    })
    .catch((error) => {
      $location.path("/Authority");
    });

  $scope.authority_of = function (acc, role) {
    if ($scope.authorities) {
      return $scope.authorities.find(
        (ur) =>
          ur.account.userName == acc.userName && ur.role.roleId == role.roleId,
      );
    }
  };

  $scope.authority_changed = function (acc, role) {
    var authority = $scope.authority_of(acc, role);
    if (authority) {
      // cap quyen => thu hoi quyen
      $scope.revoke_authority(authority);
    } else {
      // chua co quyen => cap quyen
      authority = { account: acc, role: role };
      $scope.grant_authority(authority);
    }
  };

  // them quyen
  $scope.grant_authority = function (authority) {
    $http
      .post(`/rest/authorities`, authority)
      .then((resp) => {
        $scope.authorities.push(resp.data);
        alert("Cấp quyền sử dụng thành công");
      })
      .catch((error) => {
        alert("Cấp quyền sử dụng thất bại");
        console.log("Error", error);
      });
  };

  // xoa quyen
  $scope.revoke_authority = function (authority) {
    $http
      .delete(`/rest/authorities/${authority.authorityID}`)
      .then((resp) => {
        var index = $scope.authorities.findIndex(
          (a) => a.id == authority.authorityID,
        );
        console.log(authority.authorityID);
        $scope.authorities.splice(index, 1);
        alert("Thu hồi quyền thành công");
      })
      .catch((error) => {
        alert("Thu hồi quyền thất bại");
        console.log("Error", error);
      });
  };

  $scope.initialize();
});
