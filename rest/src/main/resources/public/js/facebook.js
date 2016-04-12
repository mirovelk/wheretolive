// This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
        // Logged into your app and Facebook.
        loginOnServer(response);
    } else if (response.status === 'not_authorized') {
        // The person is logged into Facebook, but not your app.
        console.log("user not authorized for the app");
    } else {
        logoutOnServer();
        // The person is not logged into Facebook, so we're not sure if
        // they are logged into this app or not.
        console.log("user not logged in to facebook");
    }
}

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });
}


function loginOnServer(response) {
    var facebookAuthToken = response.authResponse.accessToken;
    var facebookId = response.authResponse.userID;
    var data = {
        facebookAuthToken: facebookAuthToken,
        facebookId: facebookId
    };
    $.getJSON("person/login", data, function (data, status) {
        loadSettings(data);
        refreshRealities();
    });
}

function logoutOnServer() {
    $.getJSON("person/logout", {}, function (data, status) {
        refreshRealities();
    });
}