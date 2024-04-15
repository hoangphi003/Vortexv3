TechVortex.controller("AccountController", function ($scope, AccountService) {
  const Base64 = {
    encode: function (str) {
      return btoa(unescape(encodeURIComponent(str)));
    },
    decode: function (str) {
      return decodeURIComponent(escape(atob(str)));
    },
  };

  $scope.sessionId = "";
  $scope.profile = {};
  $scope.avatarFile = null;
  $scope.errorMessageEmail = "";
  $scope.errorMessagePhone = "";

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
    AccountService.updateAccount($scope.profile.userName, $scope.profile).then(
      function (response) {
        $scope.notifierSuccess("Cập nhật thành công!");
      },
      function (error) {
        console.log("Lỗi cập nhật tài khoản:", error);
      },
    );

    if ($scope.avatarFile) {
      AccountService.updateAvatar(
        $scope.profile.userName,
        $scope.avatarFile,
      ).then(
        function (response) {},
        function (error) {
          console.log("Lỗi cập nhật hình đại diện:", error);
        },
      );
    }
  };

  $scope.fileChanged = function (element) {
    $scope.avatarFile = element.files[0];
    showAvatar(element);
  };

  function showAvatar(input) {
    var avatarImage = document.getElementById("avatarImage");
    var defaultAvatarImage = document.getElementById("defaultAvatarImage");

    if (input.files && input.files[0]) {
      var reader = new FileReader();

      reader.onload = function (e) {
        avatarImage.src = e.target.result;
        defaultAvatarImage.style.display = "none"; // Ẩn ảnh mặc định
      };

      reader.readAsDataURL(input.files[0]);
    }
  }

  // Initial load
  $scope.getSessionId();
});
