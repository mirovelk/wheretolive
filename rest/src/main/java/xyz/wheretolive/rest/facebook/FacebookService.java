package xyz.wheretolive.rest.facebook;

import java.util.logging.Logger;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

public class FacebookService {

    final static Logger logger = Logger.getLogger(FacebookService.class.getName());

    private final Facebook facebook;

    public FacebookService(String accessToken) {
        facebook = new FacebookTemplate(accessToken);
    }

    public User downloadUserProfile() {
        return facebook.userOperations().getUserProfile();
    }
}
