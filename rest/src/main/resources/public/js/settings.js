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
    var minSize = sizeSlider.noUiSlider.get()[0];
    var maxSize = sizeSlider.noUiSlider.get()[1];
    var minPrice = priceSlider.noUiSlider.get()[0];
    var maxPrice = priceSlider.noUiSlider.get()[1];
    var minPricePerSquaredMeter = pricePerSquaredMeterSlider.noUiSlider.get()[0];
    var maxPricePerSquaredMeter = pricePerSquaredMeterSlider.noUiSlider.get()[1];
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

function openSettings() {
    var map = $('#map');
    map.css({"opacity" : 0.5});
    $('#settings').fadeToggle('300');
    logSettingsOpen();
}

function closeSettings() {
    var map = $('#map');
    map.css({"opacity" : 1})
    $('#settings').fadeToggle('300');
    updateSettings();
    logSettingsClosed();
}

function toggleSettings() {
    var map = $('#map');
    var mapOpacity = map.css("opacity");
    if (mapOpacity == "1") {
        openSettings();
    } else {
        closeSettings();
    }
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
    sizeSlider.noUiSlider.destroy();
    priceSlider.noUiSlider.destroy();
    pricePerSquaredMeterSlider.noUiSlider.destroy();
    initSliders();
}

function initSliders() {
    initSizeSlider();
    initPriceSlider();
    initPricePerSquaredMeterSlider();
    sizeSlider.noUiSlider.on('set', function(){
        filter();
        var minSize = sizeSlider.noUiSlider.get()[0];
        logFilterChanged('minSize', minSize);
    });
    priceSlider.noUiSlider.on('set', function(){
        filter();
        var maxPrice = priceSlider.noUiSlider.get()[1];
        logFilterChanged('maxPrice', maxPrice);
    });
    pricePerSquaredMeterSlider.noUiSlider.on('set', function(){
        filter();
        var maxPricePerSquaredMeter = pricePerSquaredMeterSlider.noUiSlider.get()[1];
        logFilterChanged('maxPricePerSquaredMeter', maxPricePerSquaredMeter);
    });
}

var sizeSlider;
var priceSlider;
var pricePerSquaredMeterSlider;

function initSizeSlider() {
    sizeSlider = document.getElementById("sizeSlider");
    noUiSlider.create(sizeSlider, {
        connect: true,
        behaviour: 'tap',
        tooltips: [ wNumb({ decimals: 0 }), wNumb({ decimals: 0 }) ],
        start: [ parseInt(clientSettings.sizeMin), parseInt(clientSettings.sizeMax) ],
        range: {
            'min': [ parseInt(applicationSettings.sizeRangeMin) ],
            'max': [ parseInt(applicationSettings.sizeRangeMax) ]
        },
        step: 10
    });
}

function initPriceSlider() {
    priceSlider = document.getElementById("priceSlider");
    noUiSlider.create(priceSlider, {
        connect: true,
        behaviour: 'tap',
        tooltips: [ wNumb({ decimals: 0 }), wNumb({ decimals: 0 }) ],
        start: [ parseInt(clientSettings.priceMin), parseInt(clientSettings.priceMax) ],
        range: {
            'min': [ parseInt(applicationSettings.priceRangeMin) ],
            'max': [ parseInt(applicationSettings.priceRangeMax) ]
        },
        step: 1000
    });
}

function initPricePerSquaredMeterSlider() {
    pricePerSquaredMeterSlider = document.getElementById("pricePerSquaredMeterSlider");
    noUiSlider.create(pricePerSquaredMeterSlider, {
        connect: true,
        behaviour: 'tap',
        tooltips: [ wNumb({ decimals: 0 }), wNumb({ decimals: 0 }) ],
        start: [ parseInt(clientSettings.ppm2Min), parseInt(clientSettings.ppm2Max) ],
        range: {
            'min': [ parseInt(applicationSettings.ppm2RangeMin) ],
            'max': [ parseInt(applicationSettings.ppm2RangeMax) ]
        },
        step: 25
    });
}
