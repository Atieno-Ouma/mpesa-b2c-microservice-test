package org.example.backendtest.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsNotificationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${safaricom.consumer.key}")
    private String consumerKey;

    @Value("${safaricom.consumer.secret}")
    private String consumerSecret;

    @Value("${safaricom.token.url}")
    private String tokenUrl;

    @Value("${safaricom.sms.url}")
    private String smsUrl;

    @Value("${safaricom.sender.id}")
    private String senderId;

    public SmsNotificationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String sendSms(String phoneNumber, String message) {
        try {
            String accessToken = getAccessToken();
            if (accessToken == null) {
                return "Failed to get access token";
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
            body.put("to", phoneNumber);
            body.put("message", message);
            body.put("from", senderId); // Loaded from environment variables

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(smsUrl, HttpMethod.POST, request, String.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending SMS: " + e.getMessage();
        }
    }

    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(consumerKey, consumerSecret);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                return jsonNode.get("access_token").asText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
