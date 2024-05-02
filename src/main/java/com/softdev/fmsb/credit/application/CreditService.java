package com.softdev.fmsb.credit.application;

import com.softdev.fmsb.credit.infraestructure.CreditRepository;
import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.credit.model.CreditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    public List<CreditResponse> getCredits() {
        List<Credit> credits = creditRepository.findAll();
        List<CreditResponse> creditResponses = new ArrayList<>();

        for (Credit credit : credits) {

            CreditResponse creditResponse = CreditResponse.builder()
                    .creditId(credit.getCreditId())
                    .clientRfc(credit.getCreditApplication().getCreditApplicant().getRfc())
                    .creditType(credit.getCreditApplication().getSelectedCredit().getDescription())
                    .build();

            creditResponses.add(creditResponse);
        }

        return creditResponses;
    }

    public List<Credit> getCreditsByRfc(String rfc) {
        return creditRepository.getCreditByCreditApplication_CreditApplicant_Rfc(rfc);
    }
}
