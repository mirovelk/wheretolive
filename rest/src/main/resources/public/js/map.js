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
    $.postJSON("mapObject/all", {}, function (data, status) {
        data.map(function(item) {
            var image;
            if (item.name == "Billa") {
                image = 'img/billa.png';
            } else if (item.name == "Kaufland") {
                image = 'img/kaufland.png';
            }
            var marker = new google.maps.Marker({
                position: {lat: item.location.latitude, lng: item.location.longitude},
                map: map,
                icon: image
            });
        });
    });
}