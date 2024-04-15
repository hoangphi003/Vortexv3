angular.module("TechVortex").service("DetailService", function ($http) {
  // Đếm đánh giá
  this.getCountReview = function (productId, successCallback) {
    $http.get(`/countreview/` + productId).then((response) => {
      successCallback(response.data);
    });
  };

  // Tính trung bình đánh giá
  this.getAvgReview = function (productId, successCallback) {
    $http.get(`/avgreview/` + productId).then((response) => {
      successCallback(response.data);
    });
  };
  // Đếm đánh giá nào có 4 sao trở lên
  this.getCountReviewThen4s = function (productId, successCallback) {
    $http.get(`/countreviewthen4s/` + productId).then((response) => {
      successCallback(response.data);
    });
  };

  //   Tìm đánh giá theo sao đã chọn
  this.filterStartByUser = function (productId, start, successCallback) {
    $http
      .get(`/filterchoosestart/` + productId + "/" + start)
      .then((response) => {
         successCallback(response.data);
      })
      .catch((error) => console.log(error));
  };

  //   điểm yêu thích
  this.countFavoriteProduct = function (productId, successCallback) {
    $http
      .get(`/countfavorite/` + productId)
      .then((response) => {
        successCallback(response.data);
      })
      .catch((error) => console.log(error));
  };
});
