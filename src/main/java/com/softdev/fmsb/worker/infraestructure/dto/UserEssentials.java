package com.softdev.fmsb.worker.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEssentials {
    private String firstname;
    private String lastname;
    private String rfc;
}
