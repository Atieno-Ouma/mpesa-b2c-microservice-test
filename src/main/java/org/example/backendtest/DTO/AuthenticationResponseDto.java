package org.example.backendtest.DTO;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String accessToken;
    private String expiresIn;
}
