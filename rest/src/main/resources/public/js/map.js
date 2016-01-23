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

function createMarker(item, color) {
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
    housings.push(item);
    var link;
    if (item.name == "BezRealitky") {
        link = "<a target='_blank' href='http://www.bezrealitky.cz/nemovitosti-byty-domy/" + item.realityId + "'><h2>Bez realitky</h2></a>";
    } else if (item.name == "SReality") {
        link = "<a target='_blank' href='http://www.sreality.cz/detail/pronajem/byt/2+kk/praha/" + item.realityId + "'><h2>SReality</h2></a>";
    }
    var contentString = "<div>" + link +
        "<h3>" + item.price + "</h3>" +
        "<h3>" + item.area + "m<sup>2</sup></h3>" +
        "</div>";
    marker.addListener('click', function() {
        if (infoWindow) {
            infoWindow.close();
        }
        infoWindow = new google.maps.InfoWindow({
            content: contentString
        });
        infoWindow.open(map, marker);
    });
    //marker.addListener('mouseout', function() {
    //    if (infoWindow) {
    //        infoWindow.close();
    //    }
    //});
}

function loadHousing() {
    $.postJSON("mapObject/housing", {}, function (data, status) {
        var maxPricePerSquareMeter = 300;
        var minPricePerSquareMeter = 100;
        var difference = maxPricePerSquareMeter - minPricePerSquareMeter;
        
        data.map(function(item) {
            if (contains(housingMarkers, item)) {
                return;
            }
            var pricePerSquareMeter = item.pricePerSquaredMeter;
            var percent = (pricePerSquareMeter - minPricePerSquareMeter) / difference;
            var color = getColor(Math.max(0, Math.min(percent * 100, 100)));
            createMarker(item, color);
        });
        filter();
    });
    google.maps.event.addListener(map, 'click', function() {
        if (infoWindow) {
            infoWindow.close();
        }
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

function updateSizeSlider() {
    $.postJSON("mapObject/housingMeta", {}, function (data, status) {
        if (data.minArea == data.maxArea) {
            return;
        }
        sizeSlider.noUiSlider.updateOptions({
            range: {
                'min': [ data.minArea ],
                '0%': [ data.minArea, 5 ],
                'max': [ data.maxArea ]
            }
        });
    });
}

function updatePriceSlider() {
    $.postJSON("mapObject/housingMeta", {}, function (data, status) {
        if (data.minPrice == data.maxPrice) {
            return;
        }
        var max = Math.min(data.maxPrice, 50000);
        priceSlider.noUiSlider.updateOptions({
            range: {
                'min': [ data.minPrice ],
                '0%': [ data.minPrice, 5000 ],
                'max': [ max ]
            }
        });
    });
}

function updatePricePerSquaredMeterSlider() {
    $.postJSON("mapObject/housingMeta", {}, function (data, status) {
        if (data.minPricePerSquaredMeter == data.maxPricePerSquaredMeter) {
            return;
        }
        var max = Math.min(data.maxPricePerSquaredMeter, 1000);
        pricePerSquaredMeterSlider.noUiSlider.updateOptions({
            range: {
                'min': [ data.minPricePerSquaredMeter ],
                '0%': [ data.minPricePerSquaredMeter, 10 ],
                'max': [ max ]
            }
        });
    });
}

function filter() {
    var minSize = sizeSlider.noUiSlider.get()[0];
    var maxSize = sizeSlider.noUiSlider.get()[1];
    var minPrice = priceSlider.noUiSlider.get()[0];
    var maxPrice = priceSlider.noUiSlider.get()[1];
    var minPricePerSquaredMeter = pricePerSquaredMeterSlider.noUiSlider.get()[0];
    var maxPricePerSquaredMeter = pricePerSquaredMeterSlider.noUiSlider.get()[1];
    for (var i = 0; i < housings.length; i++) {
        var area = housings[i].area;
        var price = housings[i].price;
        var pricePerSquaredMeter = housings[i].pricePerSquaredMeter;
        if (area < minSize || area > maxSize
            || price < minPrice || price > maxPrice
            || pricePerSquaredMeter < minPricePerSquaredMeter || pricePerSquaredMeter > maxPricePerSquaredMeter) {
            housingMarkers[i].setMap(null);
        } else if (housingMarkers[i].getMap() == null){
            housingMarkers[i].setMap(map);
        }
    }
}

function openSettings() {
    var mapOpacity = $('#map').css("opacity");
    if (mapOpacity == "1") {
        $('#map').css({"opacity" : 0.5});
    } else {
        $('#map').css({"opacity" : 1});
    }
    $('#settings').toggle();
}