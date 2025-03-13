package org.example.backendtest.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backendtest.Config.MpesaConfiguration;
import org.example.backendtest.Config.OauthConfiguration;
import org.example.backendtest.DTO.AuthenticationResponseDto;
import org.example.backendtest.DTO.AuthenticationResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service

public class AuthenticationService {

    private final ObjectMapper objectMapper;
    private final OauthConfiguration oauthConfiguration;

    public AuthenticationService(ObjectMapper objectMapper, OauthConfiguration oauthConfiguration) {
        this.objectMapper = objectMapper;
        this.oauthConfiguration = oauthConfiguration;
    }

    public AuthenticationResponseDto generateToken() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        // Generate the Basic Auth Header dynamically
        String credentials = oauthConfiguration.getClientId() + ":" + oauthConfiguration.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        String authorizationHeader = "Basic " + encodedCredentials;

        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", oauthConfiguration.getOauthEndpoint(), oauthConfiguration.getGrantType()))
                .method("GET", null)
                .addHeader("Authorization", authorizationHeader)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String accessToken = jsonNode.get("access_token").asText();
                String expiresIn = jsonNode.get("expires_in").asText();

                AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
                authenticationResponseDto.setAccessToken(accessToken);
                authenticationResponseDto.setExpiresIn(expiresIn);
                return authenticationResponseDto;
            } else {
                throw new RuntimeException("Failed to generate token: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }
}
