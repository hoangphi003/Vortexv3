TechVortex.controller(
  "CountProductController",
  function ($location, $rootScope) {
    const checkAllTop = document.getElementById("check-all-top");
    const checkAllBottom = document.getElementById("check-all-bottom");
    let total = document.querySelectorAll(".count-product");

    checkAllTop.addEventListener("click", function () {
      const inputProduct = document.querySelectorAll(".input-product");
      const checkAllTopState = checkAllTop.checked;

      inputProduct.forEach((input) => {
        if (input.checked !== checkAllTopState) {
          input.click();
        }
        input.addEventListener("change", function () {
          let countChecked = document.querySelectorAll(
            ".input-product:checked",
          ).length;

          total.forEach((item) => {
            // Cập nhật số lượng checked
            item.innerHTML = countChecked;
          });

          if (countChecked === 0) {
            checkAllTop.checked = false;
            checkAllBottom.checked = false;
          }
        });
      });

      // Ban đầu cập nhật số lượng checked
      let countChecked = document.querySelectorAll(
        ".input-product:checked",
      ).length;
      total.forEach((item) => {
        item.innerHTML = countChecked;
      });

      if (!checkAllTop.checked) {
        total.forEach((item) => {
          item.innerHTML = 0;
        });
      }
      checkAllBottom.checked = checkAllTop.checked;
    });

    checkAllBottom.addEventListener("click", function () {
      const inputProduct = document.querySelectorAll(".input-product");

      const checkAllTopState = checkAllBottom.checked;

      inputProduct.forEach((input) => {
        if (input.checked !== checkAllTopState) {
          input.click();
        }
        input.addEventListener("change", function () {
          let countChecked = document.querySelectorAll(
            ".input-product:checked",
          ).length;
          total.forEach((item) => {
            // Cập nhật số lượng checked
            item.innerHTML = countChecked;
          });
          if (countChecked === 0) {
            checkAllTop.checked = false;
            checkAllBottom.checked = false;
          }
        });
      });
      let countChecked = document.querySelectorAll(
        ".input-product:checked",
      ).length;
      total.forEach((item) => {
        item.innerHTML = countChecked;
      });
      if (!checkAllBottom.checked) {
        total.forEach((item) => {
          item.innerHTML = 0;
        });
      }
      checkAllTop.checked = checkAllBottom.checked;
    });

    
  },
);