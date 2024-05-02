package com.softdev.fmsb.credit.model;

import com.softdev.fmsb.politics.model.Politic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCreditApplicationRequest {
    private String comments;
    private List<Politic> rejectedPolicies;
    private int userId;
    private int creditApplicationId;
}
