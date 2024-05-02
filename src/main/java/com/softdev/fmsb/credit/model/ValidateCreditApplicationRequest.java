package com.softdev.fmsb.credit.model;

import com.softdev.fmsb.auth.model.User;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
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
    private User user;
    private CreditApplication creditApplication;

}
