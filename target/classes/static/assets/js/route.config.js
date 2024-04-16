TechVortex.config(function ($routeProvider, $locationProvider) {
  $routeProvider
    .when("/", {
      templateUrl: "/assets/user/home.html",
      controller: [
        "$scope",
        "$controller",
        "$routeParams",
        function ($scope, $controller, $routeParams) {
          $controller("HomeController", { $scope: $scope });
          $controller("CompareController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("LoginController", {
            $scope: $scope,
          });
          $controller("RegisterController", {
            $scope: $scope,
          });
          $controller("GoogleController", {
            $scope: $scope,
          });
          $controller("vnPayController", {
            $scope: $scope,
          });
        },
      ],
    })
    .when("/cart", {
      templateUrl: "/assets/user/cart.html",
      controller: [
        "$scope",
        "$controller",
        "$routeParams",
        function ($scope, $controller, $routeParams) {
          $controller("cartController", { $scope: $scope });
          $controller("CountProductController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("ProductDetailController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("CompareController", {
            $scope: $scope,
          });
        },
      ],
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (localStorage.getItem("token")) {
            // có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
            $location.path("/login");
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/login", {
      templateUrl: "/assets/user/login.html",
      controller: [
        "$scope",
        "$controller",
        "$routeParams",
        function ($scope, $controller, $routeParams) {
          $controller("LoginController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("GoogleController", {
            $scope: $scope,
          });
        },
      ],
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (!localStorage.getItem("token")) {
            //đã có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/register", {
      templateUrl: "/assets/user/register.html",
      controller: "RegisterController",
      controller: [
        "$scope",
        "$controller",
        "$routeParams",
        function ($scope, $controller, $routeParams) {
          $controller("RegisterController", { $scope: $scope });
          $controller("GoogleController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
        },
      ],
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (!localStorage.getItem("token")) {
            //đã có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/about", {
      templateUrl: "/assets/user/about.html",
      controller: "HomeController",
    })
    .when("/checkout", {
      templateUrl: "/assets/user/checkout.html",
      controller: "CheckoutController",
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (localStorage.getItem("token")) {
            // có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/detail/:id", {
      templateUrl: "/assets/user/product-detail.html",
      controller: [
        "$scope",
        "$controller",
        "$routeParams",
        function ($scope, $controller, $routeParams) {
          $controller("ProductDetailController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("CompareController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("FavoriteController", {
            $scope: $scope,
          });
        },
      ],
    })
    .when("/products", {
      templateUrl: "/assets/user/products.html",
      controller: "ProductsController",
    })
    .when("/profile", {
      templateUrl: "/assets/user/profile.html",
      controller: "AccountController",
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (localStorage.getItem("token")) {
            // có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
            $location.path("/login");
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/purchase", {
      templateUrl: "/assets/user/purchase.html",
      controller: [
        "$scope",
        "$controller",
        "$routeParams",
        function ($scope, $controller, $routeParams) {
          $controller("PurchaseController", { $scope: $scope });
          $controller("ReviewProductController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("AccountController", {
            $scope: $scope,
          });
          $controller("PurchaseOrderController", {
            $scope: $scope,
          });
        },
      ],
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (localStorage.getItem("token")) {
            // có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
            $location.path("/login");
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/purchasedetail", {
      templateUrl: "/assets/user/purchasedetail.html",
      controller: [
        "$scope",
        "$controller",
        "$routeParams",
        function ($scope, $controller, $routeParams) {
          $controller("PurchaseOrderController", { $scope: $scope });
          $controller("ReviewProductController", {
            $scope: $scope,
            $routeParams: $routeParams,
          });
          $controller("AccountController", {
            $scope: $scope,
          });
        },
      ],
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (localStorage.getItem("token")) {
            // có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
            $location.path("/login");
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/compare", {
      templateUrl: "/assets/user/compare.html",
      controller: "CompareController",
    })
    .when("/vnpaysuccess", {
      templateUrl: "/assets/user/vnpaysuccess.html",
      controller: "vnPayController",
    })
    .when("/vnpayfail", {
      templateUrl: "/assets/user/vnpayfail.html",
      controller: "vnPayController",
    })
    .when("/changepassword", {
      templateUrl: "/assets/user/changepassword.html",
      controller: "ChangePasswordController",
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (localStorage.getItem("token")) {
            // có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
            $location.path("/login");
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .when("/forgetpassword", {
      templateUrl: "/assets/user/forgetpassword.html",
      controller: "ForgotPasswordController",
    })
    .when("/search", {
      templateUrl: "/assets/user/search.html",
      controller: "ProductsSearch",
    })
    .when("/contact", {
      templateUrl: "/assets/user/contact.html",
      controller: "HomeController",
    })
    .when("/faqs", {
      templateUrl: "/assets/user/FAQs.html",
      controller: "HomeController",
    })
    .when("/blog", {
      templateUrl: "/assets/user/blog.html",
      controller: "HomeController",
    })
    .when("/favorite", {
      templateUrl: "/assets/user/favorite.html",
      controller: "FavoriteController",
      // Resolve chỉ định phần được giải quyết
      resolve: {
        auth: function ($location, $q) {
          let deferred = $q.defer();
          if (localStorage.getItem("token")) {
            // có token cho phép chuyển trang chỉ định
            deferred.resolve();
          } else {
            deferred.reject();
            $location.path("/login");
          }
          // Khi tạo ra promise khi trả về cũng là promise
          return deferred.promise;
        },
      },
    })
    .otherwise({
      templateUrl: "/assets/user/error.html",
      controller: "HomeController",
    });

  // $locationProvider.html5Mode(true).hashPrefix("");
});
// .run([
//   "$rootScope",
//   function ($rootScope) {
//     // Xử lý sự kiện khi người dùng click vào các link
//     $rootScope.$on("$locationChangeSuccess", function (event) {
//       // Đảm bảo rằng route của AngularJS được xử lý đúng
//       event.preventDefault();
//     });
//   },
// ]);
