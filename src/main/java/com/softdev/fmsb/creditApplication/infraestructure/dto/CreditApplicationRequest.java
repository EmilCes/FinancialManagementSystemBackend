package com.softdev.fmsb.creditApplication.infraestructure.dto;

import com.softdev.fmsb.creditApplication.model.Reference;
import com.softdev.fmsb.creditType.model.CreditType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditApplicationRequest
{
    private String clientRfc;
    private CreditType selectedCredit;
    private Reference firstReference;
    private Reference secondReference;
    private byte[] identificationPdf;
    private byte[] proofOfIncomePdf;
    private byte[] proofOfAddressPdf;
}
