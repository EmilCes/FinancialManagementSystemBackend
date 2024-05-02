package com.softdev.fmsb.worker.infraestructure.dto;

import com.softdev.fmsb.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String rfc;
    private String userNumber;
    private Role role;
    private boolean mfaEnabled;

}
