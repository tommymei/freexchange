
/* 
 * Sends the form as a JSON request to the server:
 *   url - the url to post the JSON request to
 *   func - the function to call on success
 */
function sendJson(url, func) {
    var values = {};
    
    $('input[type != submit]').each(function() {
        values[this.name] = $(this).val();
    });
	
    $.ajax({
        url: url,
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(values),
        type: 'post',
        success: func
    });
}

/* 
 * Sends a JSON request to the server:
 *   values - the data to send in the JSON request
 *   url - the url to post the JSON request to
 *   func - the function to call on success 
 */
function sendJson(values, url, func) {
    $.ajax({
        url: url,
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(values),
        type: 'post',
        success: func
    });
}
