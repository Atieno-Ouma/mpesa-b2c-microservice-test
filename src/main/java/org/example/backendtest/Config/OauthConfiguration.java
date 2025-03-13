package org.example.backendtest.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OauthConfiguration {

    @Value("${OAUTH_CLIENT_ID}")
    private String clientId;

    @Value("${OAUTH_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${OAUTH_GRANT_TYPE}")
    private String grantType;

    @Value("${OAUTH_ENDPOINT}")
    private String oauthEndpoint;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getOauthEndpoint() {
        return oauthEndpoint;
    }
}
