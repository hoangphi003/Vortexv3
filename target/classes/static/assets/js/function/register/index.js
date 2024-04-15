TechVortex.controller("RegisterController", function ($scope, $http) {
  // initial
  $scope.username = "";
  $scope.password = "";
  $scope.email = "";

  // message error
  $scope.messageUser = "";
  $scope.messageEmail = "";
  $scope.messagePassword = "";

  // border class
  $scope.usernameBorder = "";
  $scope.passwordBorder = "";
  $scope.emailBorder = "";

  $scope.registerSubmit = function () {
    if (validateForm()) {
      $scope.user = {
        userName: $scope.username,
        email: $scope.email,
        password: $scope.password,
      };
      $http
        .post("/registerform/", $scope.user, {
          headers: { "Content-Type": "application/json" },
        })
        .then((Response) => {
          $scope.alertMessageSuccess("Bạn đã đăng ký thành công");
        })
        .catch((error) => {
          $scope.messagePassword = "Tài khoản đã tồn tại";
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

    if ($scope.email === "") {
      $scope.messageEmail = "Nhập mật email";
      $scope.emailBorder = "red-border";
      return false;
    } else if (!isValidEmail($scope.email)) {
      $scope.messageEmail = "Email không đúng định dạng";
      $scope.emailBorder = "red-border";
      return false;
    } else {
      $scope.messageEmail = "";
      $scope.emailBorder = "";
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

  function isValidEmail(email) {
    let emailRegex = /^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  $scope.resetErrors = function () {
    $scope.messageUser = "";
    $scope.messagePassword = "";
    $scope.messageEmail = "";

    $scope.usernameBorder = "";
    $scope.passwordBorder = "";
    $scope.emailBorder = "";
  };

  $scope.loginByFacebook = function () {
    FB.login(function (response) {
      if (response.authResponse) {
        const encode = btoa(JSON.stringify(response.authResponse.userID));
        localStorage.setItem("token", encode);
        location.href = "https://localhost:443/#!/";
      } else {
        console.log("Đăng nhập thất bại hoặc bị hủy.");
      }
    });
  };
});
