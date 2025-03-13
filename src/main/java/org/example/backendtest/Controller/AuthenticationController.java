package org.example.backendtest.Controller;
import org.example.backendtest.DTO.AuthenticationResponseDto;
import org.example.backendtest.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Authentication")

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/token")
    public AuthenticationResponseDto generateToken() {
        return authenticationService.generateToken();
    }

}
