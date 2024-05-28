package com.softdev.fmsb.monhtlyEfficiencies.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthResponse {
    private Month month;
    private double expectedAmount;
    private double actualAmount;
}
