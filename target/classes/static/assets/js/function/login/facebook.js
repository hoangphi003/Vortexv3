// Khai báo Facebook SDK
window.fbAsyncInit = function () {
  FB.init({
    appId: "922087842909332",
    cookie: true,
    xfbml: true,
    version: "v13.0",
  });

  FB.AppEvents.logPageView();
};

// Load SDK của Facebook
(function (d, s, id) {
  var js,
    fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) {
    return;
  }
  js = d.createElement(s);
  js.id = id;
  js.src = "https://connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
})(document, "script", "facebook-jssdk");
