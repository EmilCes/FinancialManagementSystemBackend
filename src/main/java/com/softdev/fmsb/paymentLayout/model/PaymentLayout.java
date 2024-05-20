package com.softdev.fmsb.paymentLayout.model;

import com.softdev.fmsb.creditType.model.CreditType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLayout {
    private int paymentLayoutId;
    private String clientRfc;
    private String clientName;
    private Date startDate;
    private CreditType creditType;

}
