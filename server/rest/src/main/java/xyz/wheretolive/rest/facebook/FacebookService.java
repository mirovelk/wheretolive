package xyz.wheretolive.rest.facebook;

import java.util.logging.Logger;

import org.springframework.context.annotation.Scope;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FacebookService {

    final static Logger logger = Logger.getLogger(FacebookService.class.getName());

    private Facebook facebook;

    public void init(String accessToken) {
        facebook = new FacebookTemplate(accessToken);
    }

    public User getUserProfile() {
        return facebook.userOperations().getUserProfile();
    }
}
