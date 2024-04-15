TechVortex.controller(
  "vnPayController",
  function ($scope, $rootScope, $route, $http, $location, $window) {
    let urlParams = new URLSearchParams($window.location.search);
    let transactionStatus = urlParams.get("vnp_TransactionNo");
    const checkout = localStorage?.getItem("checkoutVortex");

    const checkoutParse = JSON?.parse(checkout);

    if (transactionStatus) {
      if (transactionStatus === "0") {
        localStorage.removeItem("checkoutVortex");
        window.location.replace("http://localhost:8080/#!/cart");
        $location.path("/cart");
      } else {
        window.location.replace("http://localhost:8080/#!/vnpaysuccess");
        $location.path("/vnpaysuccess");
        if (checkoutParse.discount == null) {
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
              checkoutParse.discount,
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
