$.postMapJSON = function(url, callback) {
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
    $.postJSON(url, data, callback);
};

$.postJSON = function(url, data, callback) {
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
        'success': callback
    });
};

var foodMarketMarkers = [];
function loadFoodMarkets() {
    ensureZoomOutClearsMap(function(){
        $.postMapJSON("mapObject/foodMarkets", function (data, status) {
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
    });
}

var housingMarkers = {};
var housings = {};
var infoWindow;
var housingMeta;

function downloadRealities(callback) {
    $.postMapJSON("reality/get", function (data, status) {
        callback(data);
    });
    google.maps.event.addListener(map, 'click', function() {
        if (infoWindow) {
            infoWindow.close();
        }
    });
}
    
function loadHousingMetaData(callback) {
    ensureZoomOutClearsMap(function() {
         $.postMapJSON("reality/meta", function (data, status) {
            housingMeta = data;
            callback();
        });
    });
}

function ensureZoomOutClearsMap(callback) {
    var zoomLevel = map.getZoom();
    if (zoomLevel < 16) {
        clearAll();
    } else {
        callback();
    }
}

function updateSettings() {
    $.postJSON("person/settings", clientSettings, function (data, status) {
        
    });
}

function loadCrawlerLogs(callback) {
    $.getJSON("metadata/crawlerLog", {}, function (data, status) {
        callback(data);
    });
}