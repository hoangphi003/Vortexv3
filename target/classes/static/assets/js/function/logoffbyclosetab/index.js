window.addEventListener("beforeunload", function (event) {
  localStorage.removeItem("Vortex");
  localStorage.removeItem("compare");
});
