function center() {
    navigator.geolocation.getCurrentPosition(function(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        var latLng = {lat: latitude, lng: longitude};
        map.setCenter(latLng);
    });
}

$.postJSON = function(url, requestData, callback) {
    var bounds = map.getBounds();
    var mapView = {
        "northEast": {
            "latitude": bounds.getNorthEast().lat(),
            "longitude": bounds.getNorthEast().lng()
        },
        "southWest": {
            "latitude": bounds.getSouthWest().lat(),
            "longitude": bounds.getSouthWest().lng()
        }
    };
    var data = mapView;
    return $.ajax({
        'type': 'POST',
        'url': url,
        'contentType': 'application/json',
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': callback,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
};

var foodMarketMarkers = [];
function loadFoodMarkets() {
    $.postJSON("mapObject/foodMarkets", {}, function (data, status) {
        data.map(function(item) {
            if (contains(foodMarketMarkers, item)) {
                return;
            }
            var image;
            if (item.name == "Billa") {
                image = 'img/billa.png';
            } else if (item.name == "Kaufland") {
                image = 'img/kaufland.png';
            } else if (item.name == "Tesco") {
                image = 'img/tesco.png';
            }
            var marker = new google.maps.Marker({
                position: {lat: item.location.latitude, lng: item.location.longitude},
                map: map,
                icon: image
            });
            foodMarketMarkers.push(marker);
        });
    });
}

var housingMarkers = []
function loadHousing() {
    $.postJSON("mapObject/housing", {}, function (data, status) {
        var maxPricePerSquareMeter = 300;
        var minPricePerSquareMeter = 100;
        var difference = maxPricePerSquareMeter - minPricePerSquareMeter;
        
        data.map(function(item) {
            if (contains(housingMarkers, item)) {
                return;
            }
            var pricePerSquareMeter = item.price / item.area;
            var percent = (pricePerSquareMeter - minPricePerSquareMeter) / difference;
            var color = getColor(Math.max(0, Math.min(percent * 100, 100)));
            var marker = new google.maps.Marker({
                position: {lat: item.location.latitude, lng: item.location.longitude},
                map: map,
                icon: {
                    path: google.maps.SymbolPath.CIRCLE,
                    scale: 3,
                    fillColor: color,
                    strokeColor: color,
                    fillOpacity: 1
                }
            });
            housingMarkers.push(marker);
            if (item.name == "BezRealitky") {
                var link = "<div><a href='http://www.bezrealitky.cz/nemovitosti-byty-domy/" + item.realityId + "'><h2>Bez realitky</h2></a>";
            } else if (item.name == "SReality") {
                var link = "<div><a href='http://www.sreality.cz/detail/pronajem/byt/2+kk/praha/" + item.realityId + "'><h2>SReality</h2></a>";
            }
            var contentString = link +
                    "<h3>" + item.price + "</h3>" +
                    "<h3>" + item.area + "m<sup>2</sup></h3>" +
                    "</div>";
            var infoWindow = new google.maps.InfoWindow({
                content: contentString
            });
            marker.addListener('click', function() {
                infoWindow.open(map, marker);
            });
        });
    });
}

function getColor(percent) {
    var r = Math.floor((255 * percent) / 100);
    var g = Math.floor((255 * (100 - percent)) / 100);
    var b = 0;
    return "rgb(" + r + "," + g + "," + b + ")";
}

function contains(markers, item) {
    for (var i = 0; i < markers.length; i++) {
        if (Math.abs(markers[i].position.lat() - item.location.latitude) < 0.000001
        && Math.abs(markers[i].position.lng() - item.location.longitude) < 0.000001) {
            return true;
        }
    }
    return false;
}
