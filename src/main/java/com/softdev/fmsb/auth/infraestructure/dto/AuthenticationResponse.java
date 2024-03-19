package com.softdev.fmsb.auth.infraestructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) //If 2fa is not enabled, json will not return secretImageUri empty
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private boolean mfaEnabled;
    private String secretImageUri;

}