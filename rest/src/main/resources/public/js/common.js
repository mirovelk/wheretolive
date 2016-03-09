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

