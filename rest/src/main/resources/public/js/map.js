function center() {
    navigator.geolocation.getCurrentPosition(function(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        var latLng = {lat: latitude, lng: longitude};
        map.setCenter(latLng);
    });
}

function visitReality(name, realityId) {
    $.getJSON("mapObject/visitHousing", {name: name, realityId: realityId}, function (data, status) {
        
    });
}

function createHousingMarker(item, color) {
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
    var link = "<a target='_blank' onclick='setVisited(" + (housings.length - 1) + ");' href='" + getRealEstateBaseUrl(item.name) + item.realityId + "'><h2>" + getRealEstateName(item.name) + "</h2></a>";
    var contentString = "<div>" + link +
        "<h3>" + item.price + "</h3>" +
        "<h3>" + item.area + "m<sup>2</sup></h3>" +
        "<h6><a href='#' onclick='dontShow(" + (housings.length - 1) + ");'><img src='../img/delete.gif'>Don't show again</a></h6>" +
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

function dontShow(index) {
    var item = housings[index];
    $.getJSON("mapObject/hide", {name: item.name, realityId: item.realityId}, function (data, status) {
        housingMarkers[index].setMap(null);
        item.hide = true;
    });
}

function setVisited(index) {
    var marker = housingMarkers[index];
    marker.setIcon({
        path: google.maps.SymbolPath.CIRCLE,
        scale: 3,
        fillColor: "rgb(0,0,255)",
        strokeColor: "rgb(0,0,255)",
        fillOpacity: 1
    });
    var item = housings[index];
    visitReality(item.name, item.realityId);
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
    } else {
        return "#";
    }
}

function formatDate(timestamp) {
    var date = new Date(timestamp);
    return date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear() + " " + twoDigits(date.getHours()) + ":" + twoDigits(date.getMinutes());
}

function twoDigits(number) {
    if (number < 10) {
        return "0" + number;
    } else {
        return number;
    }
}

function getPercentage(item) {
    switch (coloringScheme) {
        case "ppm2":
            var maxPricePerSquareMeter = 350;
            var minPricePerSquareMeter = 100;
            var difference = maxPricePerSquareMeter - minPricePerSquareMeter;
            var percent = (item.pricePerSquaredMeter - minPricePerSquareMeter) / difference;
            break;
        case "price":
            var maxPrice = 30000;
            var minPrice = 10000;
            var difference = maxPrice - minPrice;
            var percent = (item.price - minPrice) / difference;
            break;
        case "size":
            var maxSize = 150;
            var minSize = 40;
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
    var r = Math.floor((255 * percent) / 100);
    var g = Math.floor((255 * (100 - percent)) / 100);
    var b = 0;
    return "rgb(" + r + "," + g + "," + b + ")";
}

var coloringScheme = "time";
function setColoringScheme(scheme) {
    coloringScheme = scheme;
    for (var i = 0; i < housingMarkers.length; i++) {
        housingMarkers[i].setMap(null);
    }
    housingMarkers = [];
    housings = [];
    loadHousing();
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

