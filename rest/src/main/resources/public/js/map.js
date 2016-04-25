function center() {
    navigator.geolocation.getCurrentPosition(function(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        var latLng = {lat: latitude, lng: longitude};
        map.setCenter(latLng);
    }, function(error) {
        var latitude = 50.0860384;
        var longitude = 14.4222704;
        var latLng = {lat: latitude, lng: longitude};
        map.setCenter(latLng);
    }, {timeout: 10000});
}

function visitReality(name, realityId) {
    $.getJSON("reality/visit", {name: name, realityId: realityId}, function (data, status) {

    });
}

function createHousingMarker(item, icon) {
    var marker = new google.maps.Marker({
        position: {lat: item.location.latitude, lng: item.location.longitude},
        map: map,
        icon: icon
    });
    var uniqueId = getUniqueRealityId(item);
    housingMarkers[uniqueId] = marker;
    housings[uniqueId] = item;
    var link = "<a target='_blank' onclick='setVisited(\"" + uniqueId + "\");' href='" + getRealEstateBaseUrl(item.name) + item.realityId + "'><h3>" + getRealEstateName(item.name) + "</h3></a>";
    var contentString = "<div>" + link +
        "<h3>" + item.price + "</h3>" +
        "<h3>" + item.area + "m<sup>2</sup></h3>" +
        "<h6><a href='#' onclick='favorite(\"" + uniqueId + "\");'><img src='../img/favorite.png'>Favorite</a></h6>" +
        "<h6><a href='#' onclick='dontShow(\"" + uniqueId + "\");'><img src='../img/delete.gif'>Don't show again</a></h6>" +
        "<h6>" + formatDate(item.updateTime) + "</h6>" +
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
}

function getUniqueRealityId(item) {
    return item.name + "_" + item.realityId;
}

function favorite(uniqueName) {
    var item = housings[uniqueName];
    var marker = housingMarkers[uniqueName];
    $.getJSON("reality/favorite", {name: item.name, realityId: item.realityId}, function (data, status) {
    });
    if (JSON.stringify(marker.icon) === JSON.stringify(item.icon)) {
        //is favorite
        logFavoriteReality(uniqueName);
        var image = '../img/favorite.png';
        marker.setIcon(image);
    } else {
        //is not favorite
        logNotFavoriteReality(uniqueName);
        marker.setIcon(item.icon);
    }
    //FIXME: suspicious
    marker.setMap(null);
    marker.setMap(map);
}

function dontShow(uniqueName) {
    var item = housings[uniqueName];
    $.getJSON("reality/hide", {name: item.name, realityId: item.realityId}, function (data, status) {
    });
    housingMarkers[uniqueName].setMap(null);
    item.hide = true;
    logHideReality(uniqueName);
}

function setVisited(uniqueName) {
    var marker = housingMarkers[uniqueName];
    marker.setIcon({
        path: google.maps.SymbolPath.CIRCLE,
        scale: 3,
        fillColor: "rgb(0,0,255)",
        strokeColor: "rgb(0,0,255)",
        fillOpacity: 1
    });
    var item = housings[uniqueName];
    visitReality(item.name, item.realityId);
    logVisitedReality(uniqueName);
}

function getRealEstateName(name) {
    if (name == "BezRealitky") {
        return "Bez realitky";
    } else if (name == "SReality") {
        return "SReality";
    } else if (name == "RealityMat") {
        return "RealityMat";
    } else if (name == "M&M") {
        return "M&M Reality";
    } else if (name == "REMAX") {
        return "RE/MAX";
    }
}

function getRealEstateBaseUrl(name) {
    if (name == "BezRealitky") {
        return "http://www.bezrealitky.cz/nemovitosti-byty-domy/";
    } else if (name == "SReality") {
        return "http://www.sreality.cz/detail/pronajem/byt/2+kk/praha/";
    } else if (name == "RealityMat") {
        return "http://www.realitymat.cz/estate/";
    } else if (name == "M&M") {
        return "https://www.mmreality.cz/nemovitosti/";
    } else if (name == "REMAX") {
        return "http://www.remax-czech.cz/reality/detail/";
    } else {
        return "#";
    }
}

function getPercentage(item) {
    switch (coloringScheme) {
        case "ppm2":
            var minPricePerSquaredMeter = pricePerSquaredMeterSlider.data("from");
            var maxPricePerSquaredMeter = pricePerSquaredMeterSlider.data("to");
            var difference = maxPricePerSquareMeter - minPricePerSquareMeter;
            var percent = (item.pricePerSquaredMeter - minPricePerSquareMeter) / difference;
            break;
        case "price":
            var minPrice = priceSlider.data("from");
            var maxPrice = priceSlider.data("to");
            var difference = maxPrice - minPrice;
            var percent = (item.price - minPrice) / difference;
            break;
        case "size":
            var minSize = sizeSlider.data("from");
            var maxSize = sizeSlider.data("to");
            var difference = maxSize - minSize;
            var percent = (item.area - minSize) / difference;
            break;
        case "time":
            var maxAgeInHours = 340;
            var minAgeInHours = 0;
            var difference = maxAgeInHours - minAgeInHours;
            var percent = (new Date().getTime() - item.updateTime) / 1000 / 60 / 60 / difference;
    }
    return Math.max(0, Math.min(percent * 100, 100));
}

function getColor(percent) {
    var value = percent / 100;
    var hue = ((1 - value) * 120).toString(10);
    return ["hsl(", hue, ",80%,50%)"].join("");
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

function clearAll() {
    clearRealities();
    clearFoodMarkets();
}

function loadAll() {
    loadRealities();
    loadFoodMarkets();
}

function refreshAll() {
    refreshRealities();
    refreshFoodMarkets();
}

function clearRealities() {
    for (var key in housingMarkers) {
        if (housingMarkers.hasOwnProperty(key)) {
            housingMarkers[key].setMap(null);
        }
    }
    housingMarkers = {};
    housings = {};
}

function clearFoodMarkets() {
    for(var i = 0; i < foodMarketMarkers.length; i++) {
        foodMarketMarkers[i].setMap(null);
    }
    foodMarketMarkers = [];
}

function refreshFoodMarkets() {
    clearFoodMarkets();
    loadFoodMarkets();
}

function refreshRealities() {
    loadHousingMetaData(function() {
        clearRealities();
        loadRealities();
    });
}

function loadRealities() {
    loadHousingMetaData(function() {
        downloadRealities(function (data) {
            data.map(function (item) {
                var uniqueId = getUniqueRealityId(item);
                if (uniqueId in housingMarkers) {
                    return;
                }
                if ($.inArray(uniqueId, housingMeta.visitedHousingIds) != -1) {
                    var color = "rgb(0,0,255)";
                } else {
                    var percentage = getPercentage(item);
                    var color = getColor(percentage);
                }
                var icon = {
                    path: google.maps.SymbolPath.CIRCLE,
                    scale: 3,
                    fillColor: color,
                    strokeColor: color,
                    fillOpacity: 1
                };
                item.icon = icon;
                if ($.inArray(uniqueId, housingMeta.favoriteHousingIds) != -1) {
                    icon = '../img/favorite.png';
                }
                createHousingMarker(item, icon);
            });
            filter();
        });
    });
}
