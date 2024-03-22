package com.softdev.fmsb.auth.infraestructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softdev.fmsb.auth.application.AuthenticationService;
import com.softdev.fmsb.auth.infraestructure.dto.AuthenticationRequest;
import com.softdev.fmsb.auth.infraestructure.dto.AuthenticationResponse;
import com.softdev.fmsb.auth.infraestructure.dto.RegisterRequest;
import com.softdev.fmsb.auth.infraestructure.dto.VerificationRequest;
import com.softdev.fmsb.creditApplication.infraestructure.dto.VerifyRegularClientResponse;
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

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(String email){
        try {
            boolean isEmailRegistered = authenticationService.verifyEmail(email);
            return ResponseEntity.ok(isEmailRegistered);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/verify-password-code")
    public ResponseEntity<?> verifyPasswordCode(@RequestBody VerificationRequest verificationRequest){
        try {
            boolean isCodeValid = authenticationService.verifyPasswordCode(verificationRequest);
            return ResponseEntity.ok(isCodeValid);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            boolean isPasswordChanged = authenticationService.changePassword(authenticationRequest);
            return ResponseEntity.ok(isPasswordChanged);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

}
