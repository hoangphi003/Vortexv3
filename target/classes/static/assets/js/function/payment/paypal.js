const checkout = localStorage?.getItem("checkoutVortex");
const checkoutParse = JSON?.parse(checkout);

// get url search
let urlParams = new URLSearchParams(window.location.search);
let PayerID = urlParams.get("PayerID");
if (checkout) {
  if (checkoutParse.discount) {
    fetch("/addorderdiscount/" + checkoutParse.discount, {
      method: "POST",
      body: JSON.stringify(checkoutParse),
      headers: {
        "Content-type": "application/json",
      },
    });
    localStorage.removeItem("checkoutVortex");
  } else {
    fetch("/addorder/", {
      method: "POST",
      body: JSON.stringify(checkoutParse),
      headers: {
        "Content-type": "application/json",
      },
    });
    localStorage.removeItem("checkoutVortex");
  }
}
