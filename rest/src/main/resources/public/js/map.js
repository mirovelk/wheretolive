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
        path: 'M16.002,17.746c3.309,0,6-2.692,6-6s-2.691-6-6-6   c-3.309,0-6,2.691-6,6S12.693,17.746,16.002,17.746z M16.002,6.746c2.758,0,5,2.242,5,5s-2.242,5-5,5c-2.758,0-5-2.242-5-5   S13.244,6.746,16.002,6.746z',
        path: 'M16,0C9.382,0,4,5.316,4,12.001c0,7,6.001,14.161,10.376,19.194   C14.392,31.215,15.094,32,15.962,32c0.002,0,0.073,0,0.077,0c0.867,0,1.57-0.785,1.586-0.805   c4.377-5.033,10.377-12.193,10.377-19.194C28.002,5.316,22.619,0,16,0z M16.117,29.883c-0.021,0.02-0.082,0.064-0.135,0.098   c-0.01-0.027-0.084-0.086-0.129-0.133C12.188,25.631,6,18.514,6,12.001C6,6.487,10.487,2,16,2c5.516,0,10.002,4.487,10.002,10.002   C26.002,18.514,19.814,25.631,16.117,29.883z',
        scale: 1,
        fillColor: "rgb(0,0,255)",
        strokeColor: "rgb(0,0,255)",
        fillOpacity: 1
    });
    var item = housings[uniqueName];
    visitReality(item.name, item.realityId);
    logVisitedReality(uniqueName);
}

function getRealEstateName(name) {
    switch(name) {
        case "BezRealitky":
            return "Bez realitky";
            break;
        case "SReality":
            return "SReality";
            break;
        case "RealityMat":
            return "RealityMat";
            break;
        case "M&M":
            return "M&M Reality";
            break;
        case "RE/MAX":
            return "RE/MAX";
            break;
        case "Real1":
            return "Real1";
            break;
        default:
            return "";
    }
}

function getRealEstateBaseUrl(name) {
    switch(name) {
        case "BezRealitky":
            return "http://www.bezrealitky.cz/nemovitosti-byty-domy/";
            break;
        case "SReality":
            return "http://www.sreality.cz/detail/pronajem/byt/2+kk/praha/";
            break;
        case "RealityMat":
            return "http://www.realitymat.cz/estate/";
            break;
        case "M&M":
            return "https://www.mmreality.cz/nemovitosti/";
            break;
        case "REMAX":
            return "http://www.remax-czech.cz/reality/detail/";
            break;
        case "Real1":
            return "http://www.real1.cz/detail/";
            break;
        default:
            return "#";
    }
}

function getPercentage(item) {
    switch (coloringScheme) {
        case "ppm2":
            var minPricePerSquaredMeter = pricePerSquaredMeterSlider.data("from");
            var maxPricePerSquaredMeter = pricePerSquaredMeterSlider.data("to");
            var difference = maxPricePerSquaredMeter - minPricePerSquaredMeter;
            var percent = (item.pricePerSquaredMeter - minPricePerSquaredMeter) / difference;
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
                    path: 'M16.002,17.746c3.309,0,6-2.692,6-6s-2.691-6-6-6   c-3.309,0-6,2.691-6,6S12.693,17.746,16.002,17.746z M16.002,6.746c2.758,0,5,2.242,5,5s-2.242,5-5,5c-2.758,0-5-2.242-5-5   S13.244,6.746,16.002,6.746z',
                    path: 'M16,0C9.382,0,4,5.316,4,12.001c0,7,6.001,14.161,10.376,19.194   C14.392,31.215,15.094,32,15.962,32c0.002,0,0.073,0,0.077,0c0.867,0,1.57-0.785,1.586-0.805   c4.377-5.033,10.377-12.193,10.377-19.194C28.002,5.316,22.619,0,16,0z M16.117,29.883c-0.021,0.02-0.082,0.064-0.135,0.098   c-0.01-0.027-0.084-0.086-0.129-0.133C12.188,25.631,6,18.514,6,12.001C6,6.487,10.487,2,16,2c5.516,0,10.002,4.487,10.002,10.002   C26.002,18.514,19.814,25.631,16.117,29.883z',
                    scale: 1,
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
