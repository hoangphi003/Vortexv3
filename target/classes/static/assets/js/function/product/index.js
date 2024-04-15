TechVortex.service("ProductService", function ($http) {
  var apiUrl = "/findAll";
  return {
    getYourEntities: function (page, size) {
      return $http.get(apiUrl, {
        params: { page: page, size: size },
      });
    },
  };
});
TechVortex.service("ProductServicetotal", function ($http) {
  return {
    getTotalProducts: function () {
      return $http.get("/total");
    },
  };
});
TechVortex.controller(
  "ProductsController",
  function ($scope, $http, ProductService, ProductServicetotal) {
    $scope.products = [];
    $scope.page = 0;
    $scope.size = 32;
    $scope.totalPages = 0;

    $scope.loadEntities = function () {
      ProductService.getYourEntities(
        $scope.page,
        $scope.size,
        $scope.pageCount,
      ).then(function (response) {
        $scope.products = response.data.content;
        $scope.totalPages = response.data.totalPages;
      });
    };
    $scope.pre = function () {
      if ($scope.page > 0) {
        $scope.page--;
        $scope.loadEntities();
      }
    };
    $scope.next = function () {
      if ($scope.page < $scope.totalPages - 1) {
        $scope.page++;
        $scope.loadEntities();
      }
    };
    $scope.loadMore = function () {
      $scope.size *= 2;
      $scope.loadEntities();
    };

    $scope.fetchProductsSortedByPriceDesc = function () {
      $http
        .get("/sorted-by-price-desc", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.fetchProductsSortedByPriceDesc();

    $scope.fetchProductsSortedByPriceAsc = function () {
      $http
        .get("/sorted-by-price-asc", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.fetchProductsSortedByPriceAsc();

    $scope.fetchProductsSortedByNameAsc = function () {
      $http
        .get("/sorted-by-name-asc", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.fetchProductsSortedByNameAsc();

    $scope.findmoinhat = function () {
      $http
        .get("/moinhat", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.findmoinhat();

    $scope.findcunhat = function () {
      $http
        .get("/cunhat", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.findcunhat();

    $scope.fetchProductsSortedByPrice500Asc = function () {
      $http
        .get("/sorted-by-price500-asc", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.fetchProductsSortedByPrice500Asc();

    $scope.fetchProductsSortedByPrice500Asc1 = function () {
      $http
        .get("/sorted-by-price500-asc1", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.fetchProductsSortedByPrice500Asc1();

    $scope.minPrice = null;
    $scope.maxPrice = null;
    $scope.fetchProductsInPriceRangeSortedByPriceAsc = function () {
      $http
        .get("/sorted-by-price-max-min", {
          params: {
            minPrice: $scope.minPrice,
            maxPrice: $scope.maxPrice,
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    // Initial call to fetch products
    $scope.fetchProductsInPriceRangeSortedByPriceAsc();

    $scope.fetchBrands = function () {
      $http.get("/findAllbrand").then(function (response) {
        $scope.brands = response.data;
      });
    };
    $scope.fetchBrands();

    $scope.brands = [];

    $scope.fetchFilteredProducts = function () {
      var selectedBrands = $scope.brands
        .filter(function (brand) {
          return brand.selected;
        })
        .map(function (brand) {
          return brand.brandName;
        });
      $scope.selectedBrands = selectedBrands;
      ProductService.getYourEntities($scope.selectedBrands).then(function (
        response,
      ) {
        $scope.products = response.data.content;
        $scope.totalPages = response.data.totalPages;
      });
      $http
        .get("/filtered", {
          params: {
            page: $scope.page,
            size: $scope.size,
            brands: selectedBrands,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.fetchFilteredProducts();

    $scope.fetchcategory = function () {
      $http.get("/findAllcategory").then(function (response) {
        $scope.categorys = response.data;
      });
    };
    $scope.fetchcategory();

    $scope.categorys = [];

    $scope.fetchcategory1 = function () {
      var selectedCategorys = $scope.categorys
        .filter(function (category) {
          return category.selected;
        })
        .map(function (category) {
          return category.categoryName;
        });
      $scope.selectedCategorys = selectedCategorys;
      ProductService.getYourEntities($scope.selectedCategorys).then(function (
        response,
      ) {
        $scope.products = response.data.content;
        $scope.totalPages = response.data.totalPages;
      });
      $http
        .get("/category", {
          params: {
            page: $scope.page,
            size: $scope.size,
            categorys: selectedCategorys,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.fetchcategory1();

    $scope.fetchcolor = function () {
      $http.get("/findAllcolor").then(function (response) {
        $scope.colors = response.data;
      });
    };
    $scope.fetchcolor();
    $scope.colors = [];

    $scope.byColor = function () {
      var selectedColors = $scope.colors
        .filter(function (color) {
          return color.selected;
        })
        .map(function (color) {
          return color.colorName;
        });
      $scope.selectedColors = selectedColors;
      ProductService.getYourEntities($scope.selectedColors).then(function (
        response,
      ) {
        $scope.products = response.data.content;
        $scope.totalPages = response.data.totalPages;
      });
      $http
        .get("/bycolor", {
          params: {
            page: $scope.page,
            size: $scope.size,
            colors: selectedColors,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };
    $scope.byColor();

    $scope.totalProducts = 0;

    ProductServicetotal.getTotalProducts().then(
      function (response) {
        $scope.totalProducts = response.data;
      },
      function (error) {
        console.error("Error fetching total products:", error);
      },
    );

    $scope.Brand = function () {
      $http
        .get("/api/products", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand();

    $scope.Brand1 = function () {
      $http
        .get("/api/products1", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand1();

    $scope.Brand2 = function () {
      $http
        .get("/api/products2", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand2();

    $scope.Brand3 = function () {
      $http
        .get("/api/products3", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand3();

    $scope.Brand4 = function () {
      $http
        .get("/api/products4", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand4();

    $scope.Brand5 = function () {
      $http
        .get("/api/products5", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand5();

    $scope.Brand6 = function () {
      $http
        .get("/api/products6", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand6();

    $scope.Brand7 = function () {
      $http
        .get("/api/products7", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand7();

    $scope.Brand8 = function () {
      $http
        .get("/api/products8", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand8();

    $scope.Brand9 = function () {
      $http
        .get("/api/products9", {
          params: {
            page: $scope.page,
            size: $scope.size,
          },
        })
        .then(function (response) {
          $scope.products = response.data.content;
        });
    };

    $scope.Brand9();

    $scope.loadEntities();
    // $http.get('/findAll').then(Response => {
    //     $scope.products= Response.data }).catch(error => error)
  },
);
