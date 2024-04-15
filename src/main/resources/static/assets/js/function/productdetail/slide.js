$(document).ready(function () {
  $("img").click(function () {
    $(this).addClass("border-success");
    $("img").removeClass("border-success");
  });

  $(".carousel-desc .carousel-image").eq(0).addClass("border-success");

  $("#carouselExampleIndicators").on("slid.bs.carousel", function () {
    const index = $(".carousel-item.active").index();
    $(".carousel-desc .carousel-image").removeClass("border-success");
    $(".carousel-desc .carousel-image").eq(index).addClass("border-success");
  });
});
