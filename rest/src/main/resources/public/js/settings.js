var clientSettings = {
    coloring: "time",
    sizeMin: 50,
    sizeMax: 150,
    priceMin: 10000,
    priceMax: 30000,
    ppm2Min: 0,
    ppm2Max: 500
};

var applicationSettings = {
    sizeRangeMin: 30,
    sizeRangeMax: 200,
    priceRangeMin: 5000,
    priceRangeMax: 40000,
    ppm2RangeMin: 0,
    ppm2RangeMax: 500
};

function filter() {
    var minSize = sizeSlider.data("from");
    var maxSize = sizeSlider.data("to");
    var minPrice = priceSlider.data("from");
    var maxPrice = priceSlider.data("to");
    var minPricePerSquaredMeter = pricePerSquaredMeterSlider.data("from");
    var maxPricePerSquaredMeter = pricePerSquaredMeterSlider.data("to");
    for (var key in housings) {
        if (housings.hasOwnProperty(key)) {
            var area = housings[key].area;
            var price = housings[key].price;
            var pricePerSquaredMeter = housings[key].pricePerSquaredMeter;
            if (area < minSize || area > maxSize
                || price < minPrice || price > maxPrice
                || pricePerSquaredMeter < minPricePerSquaredMeter || pricePerSquaredMeter > maxPricePerSquaredMeter) {
                housingMarkers[key].setMap(null);
            } else if (housingMarkers[key].getMap() == null && !housings[key].hasOwnProperty('hide') && housings[key].hide != true) {
                housingMarkers[key].setMap(map);
            }
        }
    }
    clientSettings.sizeMin = minSize;
    clientSettings.sizeMax = maxSize;
    clientSettings.priceMin = minPrice;
    clientSettings.priceMax = maxPrice;
    clientSettings.ppm2Min = minPricePerSquaredMeter;
    clientSettings.ppm2Max = maxPricePerSquaredMeter;
}

function toggleSettings() {
    var map = $('#map');
    var mapOpacity = map.css("opacity");
    if (mapOpacity == "1") {
        map.animate({"opacity" : 0.5});
    } else {
        map.animate({"opacity" : 1});
        updateSettings();
    }
    $('#settings').toggleClass(function() {
      if($(this).is('active')) {
        logSettingsClosed();
        updateSettings();
        return ''
      } else {
        logSettingsOpen();
        return 'active'
      }
    });
}

var coloringScheme = "time";
function setColoringScheme(scheme) {
    clientSettings.coloring = scheme;
    $("#" + coloringScheme + "SchemeButton").removeClass("success");
    coloringScheme = scheme;
    $("#" + coloringScheme + "SchemeButton").addClass("success");
    logColorSchemeChanged(scheme);
    refreshRealities();
}

function loadSettings(person) {
    clientSettings = person.settings;
    sizeSlider.data("ionRangeSlider").destroy();
    priceSlider.data("ionRangeSlider").destroy();
    pricePerSquaredMeterSlider.data("ionRangeSlider").destroy();
    initSliders();
}

function initSliders() {
    initSizeSlider();
    initPriceSlider();
    initPricePerSquaredMeterSlider();
    sizeSlider.on('change', function(){
        filter();
        var minSize = sizeSlider.data("from");
        logFilterChanged('minSize', minSize);
    });
    priceSlider.on('change', function(){
        filter();
        var maxPrice = priceSlider.data("to");
        logFilterChanged('maxPrice', maxPrice);
    });
    pricePerSquaredMeterSlider.on('change', function(){
        filter();
        var maxPricePerSquaredMeter = pricePerSquaredMeterSlider.data("to");
        logFilterChanged('maxPricePerSquaredMeter', maxPricePerSquaredMeter);
    });
}

var sizeSlider;
var priceSlider;
var pricePerSquaredMeterSlider;

function initSizeSlider() {
    sizeSlider = $("#sizeSlider");
    sizeSlider.ionRangeSlider({
        type: "double",
        grid: true,
        min: parseInt(applicationSettings.sizeRangeMin),
        max: parseInt(applicationSettings.sizeRangeMax),
        from: parseInt(clientSettings.sizeMin),
        to: parseInt(clientSettings.sizeMax),
        step: 10,
        postfix: "&nbsp;m&sup2"
    })
}

function initPriceSlider() {
    priceSlider = $("#priceSlider");
    priceSlider.ionRangeSlider({
        type: "double",
        grid: true,
        min: parseInt(applicationSettings.priceRangeMin),
        max: parseInt(applicationSettings.priceRangeMax),
        from: parseInt(clientSettings.priceMin),
        to: parseInt(clientSettings.priceMax),
        step: 1000,
        postfix: "&nbsp;Kč"
    })
}

function initPricePerSquaredMeterSlider() {
    pricePerSquaredMeterSlider = $("#pricePerSquaredMeterSlider");
    pricePerSquaredMeterSlider.ionRangeSlider({
        type: "double",
        grid: true,
        min: parseInt(applicationSettings.ppm2RangeMin),
        max: parseInt(applicationSettings.ppm2RangeMax),
        from: parseInt(clientSettings.ppm2Min),
        to: parseInt(clientSettings.ppm2Max),
        step: 25,
        postfix: " &nbsp;Kč/m&sup2"
    });
}

var showTravelingTimes = false;
function setTravelingTimes() {
    if($("#travelingTimesCheckbox").is(':checked')) {
        showTravelingTimes = true;
        colorPolygonLayer.setMap(map);
        if (lastLat != null) {
            showPolygons(lastLat, lastLng, colorPolygonLayer);
        }
    } else {
        showTravelingTimes = false;
        colorPolygonLayer.setMap(null);
    }
}
