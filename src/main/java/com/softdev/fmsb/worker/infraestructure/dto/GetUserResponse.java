package com.softdev.fmsb.worker.infraestructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softdev.fmsb.auth.model.Role;
import com.softdev.fmsb.auth.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetUserResponse {

    private String firstname;
    private String lastname;
    private String email;
    private String rfc;
    private String userNumber;
    private Role role;
}
