function validateInput(input) {
    let minValue = parseInt(input.getAttribute('min'));
    let maxValue = parseInt(input.getAttribute('max'));
    let currentValue = parseInt(input.value);

    if (currentValue < minValue) {
        input.value = minValue;
    } else if (currentValue > maxValue) {
        input.value = maxValue;
    }
}