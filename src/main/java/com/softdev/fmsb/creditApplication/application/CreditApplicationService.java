package com.softdev.fmsb.creditApplication.application;

import com.softdev.fmsb.client.infraestructure.ClientRepository;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.creditApplication.infraestructure.CreditApplicationRepository;
import com.softdev.fmsb.creditApplication.infraestructure.dto.VerifyClientRequest;
import com.softdev.fmsb.creditApplication.infraestructure.dto.VerifyRegularClientResponse;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.creditApplication.model.CreditApplicationStatus;
import com.softdev.fmsb.creditApplication.model.Reference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final ClientRepository clientRepository;

    public List<CreditApplication> getAllCreditApplication(){
        return creditApplicationRepository.findAll();
    }

    public void createCreditApplication(CreditApplication creditApplication) {
        CreditApplication savedCreditApplication = creditApplicationRepository.save(creditApplication);
        List<Reference> references = savedCreditApplication.getReferences();

        for (Reference reference : references) {
            reference.setCreditApplication(savedCreditApplication);
        }

        savedCreditApplication.setReferences(references);

        creditApplicationRepository.save(savedCreditApplication);
    }


    public Client getClientByRfc(String rfc){
        Client client = clientRepository.findClientByRfc(rfc).get();
        return client;
    }

    public VerifyRegularClientResponse verifyRegularClientResponse (VerifyClientRequest verifyClientRequest){
        Client client = clientRepository.findClientByRfc(verifyClientRequest.getRfc()).get();
        boolean isNotRegularClient = creditApplicationRepository.existsCreditApplicationByCreditApplicantAndStatus(client, CreditApplicationStatus.ACTIVE);
        return new VerifyRegularClientResponse(!isNotRegularClient);
    }
}
