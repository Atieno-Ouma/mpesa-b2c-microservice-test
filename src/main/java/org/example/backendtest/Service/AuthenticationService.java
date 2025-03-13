package org.example.backendtest.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backendtest.Config.MpesaConfiguration;
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
    private final MpesaConfiguration mpesaConfiguration;
    public AuthenticationService(ObjectMapper objectMapper, MpesaConfiguration mpesaConfiguration){
        this.objectMapper = objectMapper;
        this.mpesaConfiguration = mpesaConfiguration;
    }

public AuthenticationResponseDto generateToken() {

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    Request request = new Request.Builder()
            .url(String.format("%s?grant_type=%s", mpesaConfiguration.getOauthEndpoint(), mpesaConfiguration.getGrantType()))
            .method("GET", null)
            .addHeader("Authorization", "Basic RHJmTE8yNjdMWXhrZkV0MlBZckd3OHJzZ0FMcVVHaWJpNnA4bXNFeE5RTzdLS3JkOjI0Mmk0WHVoR2tIb1JLUXJEaGRwRDZHVXpNR3Y2ZmlQamlxQ0xIQnNXdlk4Nk1laTZDcXFueUdyVE5COFdzSFE=")
            .build();
    try (Response response = client.newCall(request).execute()) {
        if (response.isSuccessful()) {
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
