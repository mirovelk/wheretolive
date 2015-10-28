function center() {
    navigator.geolocation.getCurrentPosition(function(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        var latLng = {lat: latitude, lng: longitude};
        map.setCenter(latLng);
    });
}

$.postJSON = function(url, data, callback) {
    return $.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': data,
        'dataType': 'json',
        'success': callback,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
};

function loadMapObjects() {
    $.postJSON("ws/mapObject/all", {}, {}, function (data, status) {
        alert(status);
    });
}