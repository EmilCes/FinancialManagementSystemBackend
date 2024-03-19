package com.softdev.fmsb.auth.infraestructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softdev.fmsb.auth.application.AuthenticationService;
import com.softdev.fmsb.auth.infraestructure.dto.AuthenticationRequest;
import com.softdev.fmsb.auth.infraestructure.dto.RegisterRequest;
import com.softdev.fmsb.auth.infraestructure.dto.VerificationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        var response = authenticationService.register(request);

        /*if (request.isMfaEnabled()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.accepted().build();*/
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest){
        return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
    }

    @PostMapping("/enable-2fa")
    public ResponseEntity<?> updateMfa(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.updateMfaEnabled(request ));
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY) //If 2fa is not enabled, json will not return secretImageUri empty
    public static class AuthenticationResponse {

        private String accessToken;
        private String refreshToken;
        private boolean mfaEnabled;
        private String secretImageUri;

    }
}