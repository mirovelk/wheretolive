var housingMeta;
function loadHousingMetaData(callback) {
    $.postJSON("mapObject/housingMeta", {}, function (data, status) {
        housingMeta = data;
        callback();
    });
}

function updateSizeSlider() {
    if (housingMeta.minArea == housingMeta.maxArea) {
        return;
    }
    var max = Math.min(housingMeta.maxArea, 300);
    sizeSlider.noUiSlider.updateOptions({
        range: {
            'min': [ housingMeta.minArea ],
            '0%': [ housingMeta.minArea, 5 ],
            'max': [ max ]
        },
        step: 10
    });
}

function updatePriceSlider() {
    if (housingMeta.minPrice == housingMeta.maxPrice) {
        return;
    }
    var max = Math.min(housingMeta.maxPrice, 50000);
    priceSlider.noUiSlider.updateOptions({
        range: {
            'min': [ housingMeta.minPrice ],
            '0%': [ housingMeta.minPrice, 5000 ],
            'max': [ max ]
        },
        step: 1000
    });
}

function updatePricePerSquaredMeterSlider() {
    if (housingMeta.minPricePerSquaredMeter == housingMeta.maxPricePerSquaredMeter) {
        return;
    }
    var max = Math.min(housingMeta.maxPricePerSquaredMeter, 1000);
    pricePerSquaredMeterSlider.noUiSlider.updateOptions({
        range: {
            'min': [ housingMeta.minPricePerSquaredMeter ],
            '0%': [ housingMeta.minPricePerSquaredMeter, 10 ],
            'max': [ max ]
        },
        step: 10
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
        } else if (housingMarkers[i].getMap() == null && !housings[i].hasOwnProperty('hide') && housings[i].hide != true){
            housingMarkers[i].setMap(map);
        }
    }
}

function toggleSettings() {
    var map = $('#map');
    var mapOpacity = map.css("opacity");
    if (mapOpacity == "1") {
        map.css({"opacity" : 0.5});
    } else {
        map.css({"opacity" : 1});
    }
    $('#settings').toggle();
}
