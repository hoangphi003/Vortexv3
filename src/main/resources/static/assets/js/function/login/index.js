TechVortex.controller(
  "LoginController",
  function ($scope, $http, $location, $window, $rootScope, $route) {
    $scope.username = "";
    $scope.password = "";
    $scope.messageUser = "";
    $scope.messagePassword = "";
    $scope.usernameBorder = "";
    $scope.passwordBorder = "";

    const Base64 = {
      encode: function (str) {
        return btoa(unescape(encodeURIComponent(str)));
      },
    };

    $scope.loginSubmit = function () {
      if (validateForm()) {
        $scope.user = {
          userName: $scope.username,
          password: $scope.password,
        };
        $http
          .post("/loginform/", $scope.user, {
            headers: { "Content-Type": "application/json" },
          })
          .then((Response) => {
            if (!Response.data) {
              $scope.messagePassword = "Sai tài khoản hoặc mật khẩu";
              $scope.passwordBorder = "red-border";
              $scope.usernameBorder = "red-border";
            } else {
              // Mã hóa dữ liệu trước khi lưu vào localStorage
              const encode = Base64.encode(JSON.stringify(Response.data));
              // const encode = btoa(JSON.stringify(Response.data));
              localStorage.setItem("token", encode);
              $location.path("/");
            }
          })
          .catch((error) => {
            $scope.messagePassword = "Tài khoản hoặc mật khẩu không hợp lệ";
            $scope.passwordBorder = "red-border";
            $scope.usernameBorder = "red-border";
          });
      }
    };

    function validateForm() {
      if ($scope.username === "") {
        $scope.messageUser = "Nhập tên tài khoản";
        $scope.usernameBorder = "red-border";
        return false;
      } else {
        $scope.messageUser = "";
        $scope.usernameBorder = "";
      }
      if ($scope.password === "") {
        $scope.messagePassword = "Nhập mật khẩu";
        $scope.passwordBorder = "red-border";
        return false;
      } else {
        $scope.messagePassword = "";
        $scope.passwordBorder = "";
      }
      return true;
    }
    $scope.resetErrors = function () {
      $scope.messageUser = "";
      $scope.messagePassword = "";
      $scope.usernameBorder = "";
      $scope.passwordBorder = "";
    };
    $scope.loginByFacebook = function () {
      FB.login(function (response) {
        if (response.authResponse) {
          $scope.userFB = {
            userName: response.authResponse.userID,
          };
          $http
            .post("/facebookform/", $scope.userFB, {
              headers: { "Content-Type": "application/json" },
            })
            .then((Response) => {
              const encode = Base64.encode(JSON.stringify(Response.data));
              // const encode = btoa(JSON.stringify(Response.data));
              localStorage.setItem("token", encode);
              $location.path("/");
            })
            .catch((error) => {
              console.log(error);
            });
        } else {
          console.log("Đăng nhập thất bại hoặc bị hủy.");
        }
      });
    };
  },
);
