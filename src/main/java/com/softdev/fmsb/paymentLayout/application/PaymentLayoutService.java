package com.softdev.fmsb.paymentLayout.application;

import com.softdev.fmsb.client.infraestructure.ClientRepository;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.infraestructure.CreditRepository;
import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.creditApplication.infraestructure.CreditApplicationRepository;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.paymentLayout.model.PaymentLayout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentLayoutService {

    private final CreditApplicationRepository creditApplicationRepository;

    public List<PaymentLayout> getPaymentLayouts() {
        List<CreditApplication> creditApplications = creditApplicationRepository.findByCreditIsNotNull();
        List<PaymentLayout> paymentLayouts = new ArrayList<>();

        for (CreditApplication creditApplication : creditApplications) {
            Client client = creditApplication.getCreditApplicant();
            String fullName = client.getName() + " " + client.getLastname() + " " + client.getSurname();

            paymentLayouts.add(PaymentLayout.builder()
                            .paymentLayoutId(creditApplication.getCreditApplicationId())
                            .creditType(creditApplication.getSelectedCredit())
                            .clientName(fullName)
                            .clientRfc(client.getRfc())
                            .startDate(creditApplication.getCredit().getStartDate())
                            .build());
        }

        return paymentLayouts;
    }
}
