package com.softdev.fmsb.credit.application;

import com.softdev.fmsb.auth.infraestructure.UserRepository;
import com.softdev.fmsb.auth.model.User;
import com.softdev.fmsb.credit.infraestructure.CreditRepository;
import com.softdev.fmsb.credit.infraestructure.DictumRepository;
import com.softdev.fmsb.credit.model.*;
import com.softdev.fmsb.creditApplication.infraestructure.CreditApplicationRepository;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.creditApplication.model.CreditApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final DictumRepository dictumRepository;
    private final CreditApplicationRepository creditApplicationRepository;
    private final UserRepository userRepository;



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

    public void validateCreditApplication(ValidateCreditApplicationRequest validateCreditApplicationRequest) {

        boolean notDeniedPolitics = validateCreditApplicationRequest.getRejectedPolicies().isEmpty();

        Optional<CreditApplication> optionalCreditApplication = creditApplicationRepository.findById(validateCreditApplicationRequest.getCreditApplicationId());
        Optional<User> user = userRepository.findById(validateCreditApplicationRequest.getUserId());

        CreditApplication creditApplication = optionalCreditApplication.get();

        Dictum dictum = Dictum.builder()
                .creditApplication(creditApplication)
                .comments(validateCreditApplicationRequest.getComments())
                .deniedPolitics(validateCreditApplicationRequest.getRejectedPolicies())
                .user(user.get())
                .build();

        Dictum dictumRegistered = dictumRepository.save(dictum);

        if (notDeniedPolitics) {
            Credit credit = Credit.builder()
                    .startDate(getCurrentDate())
                    .status(CreditStatus.ACTIVE)
                    .creditApplication(creditApplication)
                    .build();

            Credit creditRegistered = creditRepository.save(credit);

            creditApplication.setCredit(creditRegistered);
        }

        creditApplication.setDictum(dictumRegistered);
        creditApplication.setStatus(notDeniedPolitics ? CreditApplicationStatus.ACTIVE : CreditApplicationStatus.DENIED);

        creditApplicationRepository.save(creditApplication);
    }

    private static Date getCurrentDate() {
        java.util.Date utilDate = new java.util.Date();

        return new Date(utilDate.getTime());
    }
}
