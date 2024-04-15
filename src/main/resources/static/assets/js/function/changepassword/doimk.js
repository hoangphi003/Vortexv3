TechVortex.service("AccountService1", function ($http) {
  var serviceUrl = "/api/changepassword";

  this.updateAccount = function (userName, profile) {
    return $http.put(serviceUrl + "/" + userName, profile);
  };
});
TechVortex.controller(
  "ChangePasswordController",
  function ($scope, AccountService1) {
    const Base64 = {
      encode: function (str) {
        return btoa(unescape(encodeURIComponent(str)));
      },
      decode: function (str) {
        return decodeURIComponent(escape(atob(str)));
      },
    };
    $scope.getSessionId = function () {
      // Lấy dữ liệu đã được mã hóa từ localStorage
      const encodedDataFromStorage = localStorage.getItem("token");
      const encodedData = Base64.decode(encodedDataFromStorage);
      const jsonData = JSON.parse(encodedData);

      $scope.profile = jsonData;

      $scope.profile.birth = new Date($scope.profile.birth);
    };

    $scope.updateAccount = function () {
      // Nếu tất cả thông tin hợp lệ, tiến hành gửi dữ liệu lên server
      if (checkName()) {
        AccountService1.updateAccount(
          $scope.profile.userName,
          $scope.profile,
        ).then(
          function (response) {
            alert("Đổi mật khẩu thành công");
          },
          function (error) {
            console.log("Lỗi cập nhật tài khoản:", error);
          },
        );
      }
    };

    function checkName() {
      console.log("A");
      var message2 = document.getElementById("message2");
      var pass = document.getElementById("password").value;
      var message3 = document.getElementById("message3");
      var regexMatKhau = /\ /;

      var confpass = document.getElementById("confirmPassword").value;
      var message4 = document.getElementById("message4");
      var regexMatKhau = /\ /;
      if (pass == "") {
        password.style.border = "1px solid red";
        message3.innerHTML = "Mật khẩu không được để trống!";
        return false;
      } else if (regexMatKhau.test(pass)) {
        password.style.border = "1px solid red";
        message3.innerHTML = "Mật khẩu không hợp lệ vì chứa khoảng cách";
        return false;
      } else {
        password.style.border = "none";
        message3.innerHTML = "";
      }
      if (confpass == "") {
        confirmPassword.style.border = "1px solid red";
        message4.innerHTML = "Mật khẩu không được để trống!";
        return false;
      } else if (regexMatKhau.test(confpass)) {
        confirmPassword.style.border = "1px solid red";
        message4.innerHTML = "Mật khẩu không hợp lệ vì chứa khoảng cách";
        return false;
      } else if (confpass != pass) {
        confirmPassword.style.border = "1px solid red";
        message4.innerHTML = "Mật khẩu không trùng khớp";
        return false;
      } else {
        confirmPassword.style.border = "none";
        message4.innerHTML = "";
        return confpass;
      }
    }
    // Initial load
    $scope.getSessionId();
  },
);
