package com.softdev.fmsb.politics.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoliticRequest {

    private Integer politicId;
    private String name;
    private String description;
    private String state;

}
