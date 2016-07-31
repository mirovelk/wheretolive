package xyz.wheretolive.core.domain;

import java.util.Date;

/**
 * Contains user access token which can be used for a long time. Also contains expiration time in seconds.
 */
public class FacebookLongTermToken {

    private String token;
    private Date expires;

    public FacebookLongTermToken() {
    }
    
    public FacebookLongTermToken(String token, Date expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

}
