TechVortex.controller("ForgotPasswordController", function ($scope, $http) {
  $scope.email = "";
  $scope.errorMessage = "";
  $scope.emailBorder = "";

  $scope.submitForgotPassword = function () {
    if (validateForm()) {
      $http
        .post("/forgotpassword", { email: $scope.email })
        .then(function (response) {
          let account = response.data;
          console.log(account);
          // Reset form and show success message
          $scope.email = "";
          $scope.errorMessage = "";
          $scope.emailBorder = "";
          // Set emailSent to true to display success message
          $scope.emailSent = true;
        })
        .catch(function (error) {
          console.log(error);
          // Handle error response
          $scope.errorMessage = "Không tìm thấy email";
          $scope.emailBorder = "red-border";
        });
    }
  };

  function validateForm() {
    if ($scope.email === "") {
      $scope.errorMessage = "Nhập email của bạn";
      $scope.emailBorder = "red-border";
      return false;
    } else {
      $scope.errorMessage = "";
      $scope.emailBorder = "";
      return true;
    }
  }

  $scope.resetErrors = function () {
    $scope.errorMessage = "";
    $scope.emailBorder = "";
  };
});
