package com.softdev.fmsb.auth.infraestructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softdev.fmsb.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VerificationResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String accessToken;
    private Role role;
    private String refreshToken;

}
