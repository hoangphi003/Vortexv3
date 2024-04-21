TechVortex.controller(
  "vnPayController",
  function ($scope, $rootScope, $route, $http, $location, $window) {
    let urlParams = new URLSearchParams($window.location.search);
    // Mã giao dịch tại vnpay
    let transactionStatus = urlParams.get("vnp_TransactionNo");
    // Mã giao dịch
    let transactionBankTranNo = urlParams.get("vnp_BankTranNo");
    // Thông tin đơn hàng
    let vnp_OrderInfo = urlParams.get("vnp_OrderInfo");
    // Số tiền
    let vnp_Amount = urlParams.get("vnp_Amount");
    // Ngày giao dịch
    let vnp_PayDate = urlParams.get("vnp_PayDate");
    //  Mã kết quả
    let vnp_TransactionStatus = urlParams.get("vnp_TransactionStatus");
    // Thông báo
    let message = "Giao dịch thành công";
    // Loại thẻ
    let vnp_CardType = urlParams.get("vnp_CardType");
    // Ngân hàng
    let vnp_BankCode = urlParams.get("vnp_BankCode");

    const checkout = localStorage?.getItem("checkoutVortex");

    const checkoutParse = JSON?.parse(checkout);
    const year = vnp_PayDate?.substring(0, 4);
    const month = vnp_PayDate?.substring(4, 6);
    const day = vnp_PayDate?.substring(6, 8);
    const hours = vnp_PayDate?.substring(8, 10);
    const minutes = vnp_PayDate?.substring(10, 12);
    const seconds = vnp_PayDate?.substring(12, 14);

    // Định dạng lại ngày và giờ theo định dạng mong muốn (YYYY-MM-DD HH:mm:ss)
    const formattedDate =
      day +
      "-" +
      month +
      "-" +
      year +
      " " +
      hours +
      ":" +
      minutes +
      ":" +
      seconds;

    if (transactionStatus) {
      $scope.transactionStatus = transactionStatus;
      $scope.transactionBankTranNo = transactionBankTranNo;
      $scope.vnp_OrderInfo = vnp_OrderInfo;
      $scope.vnp_Amount = vnp_Amount / 100;
      $scope.vnp_PayDate = formattedDate;
      $scope.vnp_TransactionStatus = vnp_TransactionStatus;
      $scope.message = message;
      $scope.vnp_CardType = vnp_CardType;
      $scope.vnp_BankCode = vnp_BankCode;
      if (transactionStatus === "0") {
        localStorage.removeItem("checkoutVortex");
        $location.path("/vnpayfail");
      } else {
        $location.path("/vnpaysuccess");
        if (checkoutParse?.discount == null) {
          $http
            .post(`/addorder/`, checkoutParse, {
              headers: { "Content-Type": "application/json" },
            })
            .then((Response) => {})
            .catch((error) => console.log(error));
          localStorage.removeItem("checkoutVortex");
        } else {
          $http
            .post(
              `/addorderdiscount/` + checkoutParse.discount,
              checkoutParse,
              {
                headers: { "Content-Type": "application/json" },
              },
            )
            .then((Response) => {})
            .catch((error) => console.log(error));
          localStorage.removeItem("checkoutVortex");
        }
      }
    }
  },
);
