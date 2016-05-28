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
        path: 'M64,13.875c-19.761,0-35.782,16.02-35.782,35.782c0,23.134,29.818,64.468,35.782,64.468  c5.965,0,35.781-41.334,35.781-64.468C99.781,29.895,83.762,13.875,64,13.875z M64,69.811c-11.186,0-20.256-9.07-20.256-20.256  c0-11.187,9.07-20.256,20.256-20.256c11.188,0,20.256,9.07,20.256,20.256C84.256,60.74,75.188,69.811,64,69.811z',
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
                    path: 'M64,13.875c-19.761,0-35.782,16.02-35.782,35.782c0,23.134,29.818,64.468,35.782,64.468  c5.965,0,35.781-41.334,35.781-64.468C99.781,29.895,83.762,13.875,64,13.875z M64,69.811c-11.186,0-20.256-9.07-20.256-20.256  c0-11.187,9.07-20.256,20.256-20.256c11.188,0,20.256,9.07,20.256,20.256C84.256,60.74,75.188,69.811,64,69.811z',
                    scale: 1,
                    fillColor: color,
                    strokeColor: color,
                    fillOpacity: 1
                };
                item.icon = icon;
                if ($.inArray(uniqueId, housingMeta.favoriteHousingIds) != -1) {
                var icon = {
                    path: 'M18.237,60.574l23.096,19.578l-7.183,29.438c-1.411,5.491,4.648,9.998,9.575,6.901L69.5,100.572l25.774,15.916  c4.79,2.955,10.844-1.408,9.576-6.902l-7.184-29.435l23.099-19.579c4.363-3.661,2.111-10.844-3.662-11.267l-30.282-2.255  L75.414,19.025c-2.112-5.211-9.577-5.211-11.832,0L52.175,47.051l-30.281,2.255C16.124,49.872,13.869,56.913,18.237,60.574z',
                    scale: 1,
                    fillColor: "rgb(255,202,40)",
                    strokeColor: "rgb(255,202,40)",
                    fillOpacity: 1
                }
                    icon = icon;
                }
                createHousingMarker(item, icon);
            });
            filter();
        });
    });
}
