function center() {
    navigator.geolocation.getCurrentPosition(function(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        var latLng = {lat: latitude, lng: longitude};
        map.setCenter(latLng);
    });
}

$.getJSON = function(url, data, headers, callback) {
    return $.ajax({
        'type': 'GET',
        'url': url,
        'contentType': 'application/json',
        'data': data,
        'dataType': 'json',
        'success': callback,
        'headers': headers
    });
};

function loadMapObjects() {
    getJSON("ws/mapObject/all", {}, {}, function (data, status) {
        
    });
}