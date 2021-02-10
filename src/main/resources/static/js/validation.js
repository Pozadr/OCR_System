// validate proceed image from URL
let proceedFromUrlError;
function validateProceedFromUrl() {
    let urlInput = $('#urlInput').val();
    if (urlInput.length === 0) {
        $('#ocrProceedMessage').show();
        proceedFromUrlError = true;
    } else {
        $('#ocrProceedMessage').hide();
        proceedFromUrlError = false;
    }
    return !(proceedFromUrlError);
}

/*
// validate
let countryGetHistoryError;
function validateGetHistory() {
    let getHistory = $('#getHistory').val();
    if (getHistory.length === 0) {
        $('#getHistoryCountryMessage').show();
        countryGetHistoryError = true;
    } else {
        $('#getHistoryCountryMessage').hide();
        countryGetHistoryError = false;
    }
    return !(countryGetHistoryError);
}

 */


// Submit filterByCountry
$('#submitFilterByCountry').click(function () {
    return validateProceedFromUrl();
});

/*
// Submit getHistory
$('#submitGetHistory').click(function () {
    return validateGetHistory();
});

 */

// proceedFromUrlButton on click
$('#proceedFromUrlButton').click(function () {
    $('#ocrProceedMessage').hide();
});

