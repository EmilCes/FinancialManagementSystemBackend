package com.softdev.fmsb.credit.model;

import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.politics.model.Politic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCreditApplicationRequest {


    private String comments;
    private Politic rejectedPolicies;

}
