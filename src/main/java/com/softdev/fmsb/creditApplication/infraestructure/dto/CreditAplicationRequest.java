package com.softdev.fmsb.creditApplication.infraestructure.dto;

import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.creditApplication.model.Reference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAplicationRequest
{
    private String clientRfc;
    private Credit selectedCredit;
    private Reference firstReference;
    private Reference secondReference;
    /*private byte[] IdentificationPdf;
    private byte[] moneyComprobantPdf;
    private byte[] locationComprobantPdf;
     */
}
