TechVortex.controller(
  "loginByPhoneController",
  function ($scope, $rootScope, $route, $http, $location, $window) {
    $scope.phone = "";
    $scope.phoneBorder = "";
    $scope.message = "";
    $scope.otp = "";
    $rootScope.randomResult;

    const phone = document.getElementById("phone");

    function randomCode() {
      let code = "1234";
      return Math.floor(Math.random() * code);
    }

    const Base64 = {
      encode: function (str) {
        return btoa(unescape(encodeURIComponent(str)));
      },
    };

    $scope.submitPhone = function () {
      $rootScope.randomResult = randomCode();
      let PhoneFormat = phone.value.replace(phone.value.substring(0, 1), "84");
      if (validate()) {
        $http
          .get("/getPhoneNumber/" + phone.value)
          .then((Response) => {
            if (Response.data) {
              $scope.message = "Số điện thoại đã tồn tại";
              $scope.phoneBorder = "red-border";
            }
            if (Response.data.length === 0) {
              $scope.message = "";
              $scope.phoneBorder = "";
              $scope.notifierSuccess(
                "Vui lòng chờ vài giây mã sẽ gửi qua số điện thoại",
              );

              // Gọi api này gửi sđt
              const myHeaders = new Headers();
              myHeaders.append("Content-Type", "application/json");
              myHeaders.append(
                "Authorization",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InN0cmluZ2VlLWFwaTt2PTEifQ.eyJqdGkiOiIxIiwiaXNzIjoiU0suMC50bGx6NUJnNmtVekZURFJOWWhNMklZaENPVHYyRnAiLCJyZXN0X2FwaSI6dHJ1ZX0.dXVMqF2EAXIe3SwPeI5x56OOCZjTRvoBKkmo9dWOk0E",
              );

              const raw = JSON.stringify({
                from: {
                  type: "external",
                  number: "842873030095",
                  alias: "STRINGEE_NUMBER",
                },
                to: [
                  {
                    type: "external",
                    number: PhoneFormat,
                    alias: "TO_NUMBER",
                  },
                ],
                answer_url: "https://example.com/answerurl",
                actions: [
                  {
                    action: "talk",
                    text: `Mã xác thực của bạn là ${$rootScope.randomResult}`,
                  },
                ],
              });

              const requestOptions = {
                method: "POST",
                headers: myHeaders,
                body: raw,
                redirect: "follow",
              };

              fetch("https://api.stringee.com/v1/call2/callout", requestOptions)
                .then((response) => response.text())
                .then((result) => console.log(result))
                .catch((error) => console.error(error));
              //End

              // Gửi số này cho hàm submitOpt()
              $rootScope.sendPhone = phone.value;
              $location.path("/loginotp");
            }
          })
          .catch((error) => console.log(error));
      }
    };

    $scope.submitOtp = function () {
      if (validateOtp()) {
        $http
          .post("/phonenumberform/" + $rootScope.sendPhone, {
            headers: { "Content-Type": "application/json" },
          })
          .then((Response) => {
            const encode = Base64.encode(JSON.stringify(Response.data));

            localStorage.setItem("token", encode);
            $location.path("/");
          })
          .catch((error) => console.log(error));
      }
    };

    function validateOtp() {
      if ($scope.otp === "") {
        $scope.message = "Vui lòng nhập mã otp";
        $scope.otpBorder = "red-border";
        return false;
      } else {
        $scope.message = "";
        $scope.otpBorder = "";
      }
      if ($rootScope.randomResult !== $scope.otp) {
        $scope.message = "Mã otp không hợp lệ";
        $scope.otpBorder = "red-border";
        return false;
      } else {
        $scope.message = "";
        $scope.otpBorder = "";
      }
      return true;
    }

    function validate() {
      if ($scope.phone === "") {
        $scope.message = "Vui lòng nhập số điện thoại";
        $scope.phoneBorder = "red-border";
        return;
      } else {
        $scope.message = "";
        $scope.phoneBorder = "";
      }
      if (
        !/^\d+$/.test(phone.value) ||
        phone.value.length !== 10 ||
        phone.value[0] !== "0"
      ) {
        $scope.message = "Số điện thoại không hợp lệ";
        $scope.phoneBorder = "red-border";
        return;
      } else {
        $scope.message = "";
        $scope.phoneBorder = "";
      }
      return true;
    }
  },
);
