$.postJSON = function(url, requestData, callback) {
    var bounds = map.getBounds();
    var data = {
        "northEast": {
            "latitude": bounds.getNorthEast().lat(),
            "longitude": bounds.getNorthEast().lng()
        },
        "southWest": {
            "latitude": bounds.getSouthWest().lat(),
            "longitude": bounds.getSouthWest().lng()
        }
    };
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

$.getJSON = function(url, requestData, callback) {
    return $.ajax({
        'type': 'GET',
        'url': url,
        'contentType': 'application/json',
        'data': requestData,
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
            } else if (item.name == "Albert") {
                image = 'img/albert.png';
            } else if (item.name == "Penny") {
                image = 'img/penny.png';
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

var housingMarkers = [];
var housings = [];
var infoWindow;

function loadHousing() {
    $.postJSON("mapObject/housing", {}, function (data, status) {

        data.map(function(item) {
            if (contains(housingMarkers, item)) {
                return;
            }
            var realityId = item.name + "_" + item.realityId;
            if ($.inArray(realityId, housingMeta.visitedHousingIds) != -1) {
                var color = "rgb(0,0,255)";
            } else {
                var percentage = getPercentage(item);
                var color = getColor(percentage);
            }
            createHousingMarker(item, color);
        });
        filter();
    });
    google.maps.event.addListener(map, 'click', function() {
        if (infoWindow) {
            infoWindow.close();
        }
    });
}
