package xyz.wheretolive.rest.facebook;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FacebookLongTermToken;

@Component
public class FacebookLongTermTokenService {

    private static final String APP_SECRET = "df703e88190c9f732aadf771087989e7";
    private static final String APP_ID = "1648440438752990";

    public static void main(String[] args) {
        new FacebookLongTermTokenService().execute("CAAXLta0YfE4BAIL7X3i8jCs1Swb7328quO5mXaVOLFCEJ3BdVrvk7WFSzwAiKd6IwRZCAWklXjtJymyW7f3tYQvjZABAPLGdAOKYbLXNQXEBA5XmuZCqwkEtYRzRlXNoTm01GtStICDY2wFbLHzG9qHyeX9efeWRyhupwkAiEWzHGZCn1wtwIoAZBKVub1yTzaWo20IdxWAZDZD",
                "1631356037135438");
    }
    
    public FacebookLongTermToken execute(String accessToken, String clientId) {
        URI build = URIBuilder
                .fromUri("https://graph.facebook.com/oauth/access_token")
                .queryParam("client_id", APP_ID)
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_secret", APP_SECRET)
                .queryParam("fb_exchange_token", accessToken)
                .build();

        String url = build.toString();

        Facebook facebook = new FacebookTemplate(accessToken);
        ResponseEntity<String> exchange = facebook.restOperations().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        String response = exchange.getBody();
        String longLivedToken = response.substring(response.indexOf("access_token=") + "access_token=".length(), response.indexOf("&expires"));
        int expiresInSeconds = Integer.parseInt(response.substring(response.indexOf("&expires=") + "&expires=".length()));
        Date expirationDate = Date.from(LocalDateTime.now().plusSeconds(expiresInSeconds).atZone(ZoneId.systemDefault()).toInstant());
        return new FacebookLongTermToken(longLivedToken, expirationDate);
    }
}
