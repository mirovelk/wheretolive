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
