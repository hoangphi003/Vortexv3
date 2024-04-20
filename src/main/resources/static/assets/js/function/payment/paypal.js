const urlParams = new URLSearchParams(window.location.search);

const PayerID = urlParams.get("PayerID");
const checkout = localStorage?.getItem("checkoutVortex");
const checkoutParse = JSON.parse(checkout);

if (PayerID && checkoutParse) {
  if (checkoutParse.discount == null) {
    fetch("/addorder/", {
      method: "POST",
      body: checkout,
      headers: {
        "Content-type": "application/json",
      },
    });
    localStorage.removeItem("checkoutVortex");
  } else {
    fetch("/addorderdiscount/" + checkoutParse.discount, {
      method: "POST",
      body: checkout,
      headers: {
        "Content-type": "application/json",
      },
    });
    localStorage.removeItem("checkoutVortex");
  }
}
