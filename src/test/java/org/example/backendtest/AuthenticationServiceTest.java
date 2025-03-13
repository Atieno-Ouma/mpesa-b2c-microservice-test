package org.example.backendtest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backendtest.Config.MpesaConfiguration;
import org.example.backendtest.DTO.AuthenticationResponseDto;
import org.example.backendtest.Service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {
    @Mock
    private MpesaConfiguration mpesaConfiguration;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mpesaConfiguration.getOauthEndpoint()).thenReturn("https://sandbox.safaricom.co.ke/oauth/v1/generate");
        when(mpesaConfiguration.getGrantType()).thenReturn("client_credentials");
    }

    @Test
    void testGenerateToken() {
        AuthenticationResponseDto response = authenticationService.generateToken();
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getExpiresIn());
    }

}
