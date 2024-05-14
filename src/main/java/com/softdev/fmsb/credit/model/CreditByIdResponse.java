package com.softdev.fmsb.credit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditByIdResponse {
    private String clientName;
    private String creditType;
    private String amountBorrowed;
    private int term;
    private Integer creditNumber;
}
