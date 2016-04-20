function logFacebookLogin(facebookId) {
    sendToGa('facebook', 'login', null, facebookId)
}

function logFacebookLogout(facebookId) {
    sendToGa('facebook', 'logout', null, facebookId)
}

function logMapDrag() {
    sendToGa('map', 'drag', null, null);
}

function logMapZoomChange() {
    sendToGa('map', 'zoom', null, null);
}

function logSettingsOpen() {
    sendToGa('settings', 'open', null, null);
}

function logSettingsClosed() {
    sendToGa('settings', 'closed', null, null);
}

function logColorSchemeChanged(scheme) {
    sendToGa('settings', 'color', null, scheme);
}

function logFilterChanged(filterName, value) {
    sendToGa('settings', 'filter', filterName, value);
}

function logVisitedReality(name) {
    sendToGa('reality', 'visit', null, name);
}

function logFavoriteReality(name) {
    sendToGa('reality', 'favorite', 'on', name);
}

function logNotFavoriteReality(name) {
    sendToGa('reality', 'favorite', 'off', name);
}

function logHideReality(name) {
    sendToGa('reality', 'hide', null, name);
}

function sendToGa(category, action, label, value) {
    ga('send', {
        hitType: 'event',
        eventCategory: category,
        eventAction: action,
        eventLabel: label,
        eventValue: value
    });
}