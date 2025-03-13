package org.example.backendtest.Config;
import org.example.backendtest.Service.MpesaB2CService;
import org.example.backendtest.Service.MpesaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;
@Configuration


public class MpesaConfiguration {
    @Bean
    public MpesaB2CService mobileMoneyService() {

        return new MpesaService();
    }
    @Value("${mpesa.oauth.endpoint}")
    private String oauthEndpoint;

    @Value("${mpesa.grant.type}")
    private String grantType;

    public String getOauthEndpoint() {
        return oauthEndpoint;
    }

    public String getGrantType() {
        return grantType;
    }

}
