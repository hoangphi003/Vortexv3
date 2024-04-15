TechVortex.controller(
  "GoogleController",
  function ($scope, $http, $location, $window, $rootScope, $route) {
    // Đăng nhập từ google
    $scope.CLIENT_ID =
      "978648910384-quhtc0ahg15a96ub4o44uqkeof7h0s87.apps.googleusercontent.com";

    $scope.LINK_GET_TOKEN = `https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile&response_type=token&state=state_parameter_passthrough_value&redirect_uri=http://localhost:8080/&client_id=${$scope.CLIENT_ID}`;

    const Base64 = {
      encode: function (str) {
        return btoa(unescape(encodeURIComponent(str)));
      },
    };
    // Sử dụng AngularJS để thực hiện các thao tác với DOM sau khi nội dung đã được tải
    angular.element(document).ready(function () {
      let signBtn = document.getElementById("googlesign");

      angular.element(signBtn).on("click", function () {
        $scope.$apply(function () {
          window.location.href = $scope.LINK_GET_TOKEN;
        });
      });
    });

    const getToken = () => {
      const url = new URLSearchParams(window.location.hash.substring(1));
      const token = url.get("access_token");
      return token;
    };
    getToken();
    const getUserInfo = async () => {
      const access_token = getToken();
      if (access_token != null) {
        const response = await fetch(
          `https://www.googleapis.com/oauth2/v3/userinfo?access_token=${access_token}`,
        );

        const data = await response.json();

        if (data) {
          $scope.user = {
            userName: data.sub,
            email: data.email,
            fullName: data.name,
          };
          $http
            .post("/googleform/", $scope.user, {
              headers: { "Content-Type": "application/json" },
            })
            .then((Response) => {
              const encode = Base64.encode(JSON.stringify(Response.data));
              localStorage.setItem("token", encode);
              window.location.replace("http://localhost:8080/#!");
            })
            .catch((error) => {
              console.log(error);
            });
        }
      }
    };

    getUserInfo();
  },
);
