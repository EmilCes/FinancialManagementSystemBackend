package com.softdev.fmsb.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MfaEnabled {

    private String email;
    private String password;
    private boolean isMfaEnabled;

}
