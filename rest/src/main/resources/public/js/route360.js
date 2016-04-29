var lastLat = null;
var lastLng = null;
function showPolygons(lat, lng, layer) {
    lastLat = lat;
    lastLng = lng;
    var travelOptions = r360.travelOptions();
    travelOptions.addSource({lat: lat, lng: lng});
    travelOptions.setTravelTimes([300, 600, 900, 1200, 1500, 1800]);
    travelOptions.setTravelType('transit');
    // call the service
    r360.PolygonService.getTravelTimePolygons(travelOptions, function (polygons) {
            layer.update(polygons);
        },
        function (status, message) {
            $("#r360-gettingstarted-error").show('fade').html("We are currently performing service \
                maintenance. The service will be available shortly.");
        });
}
