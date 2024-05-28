package com.softdev.fmsb.payment.model;

import com.softdev.fmsb.creditType.model.TermType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String clientName;
    private float pendingAmount;
    private long remainingMonths;
    private float amountForNoInterest;
    private Date monthDeadlineDate;
    private TermType termType;
}
