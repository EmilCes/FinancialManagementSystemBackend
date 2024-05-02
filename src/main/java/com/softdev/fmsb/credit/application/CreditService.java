package com.softdev.fmsb.credit.application;

import com.softdev.fmsb.auth.infraestructure.UserRepository;
import com.softdev.fmsb.auth.model.User;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.infraestructure.CreditRepository;
import com.softdev.fmsb.credit.infraestructure.DictumRepository;
import com.softdev.fmsb.credit.model.*;
import com.softdev.fmsb.creditApplication.infraestructure.CreditApplicationRepository;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.creditApplication.model.CreditApplicationStatus;
import com.softdev.fmsb.creditType.model.CreditType;
import com.softdev.fmsb.politics.infraestructure.PoliticRepository;
import com.softdev.fmsb.politics.model.Politic;
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
    private final PoliticRepository politicRepository;
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
        // Verificar si se han rechazado políticas
        boolean notDeniedPolitics = validateCreditApplicationRequest.getRejectedPolicies().isEmpty();

        // Obtener la solicitud de crédito y el usuario asociado
        Optional<CreditApplication> optionalCreditApplication = creditApplicationRepository.findById(validateCreditApplicationRequest.getCreditApplicationId());
        Optional<User> optionalUser = userRepository.findById(validateCreditApplicationRequest.getUserId());

        if (optionalCreditApplication.isPresent() && optionalUser.isPresent()) {
            CreditApplication creditApplication = optionalCreditApplication.get();
            User user = optionalUser.get();

            // Obtener las entidades Politic correspondientes a los IDs proporcionados en el JSON
            List<Politic> deniedPolitics = new ArrayList<>();
            for (Politic rejectedPolicy : validateCreditApplicationRequest.getRejectedPolicies()) {
                Integer politicId = rejectedPolicy.getPoliticId();
                Optional<Politic> optionalPolitic = politicRepository.findById(politicId);
                optionalPolitic.ifPresent(deniedPolitics::add);
            }

            // Crear y guardar un dictamen
            Dictum dictum = Dictum.builder()
                    .creditApplication(creditApplication)
                    .comments(validateCreditApplicationRequest.getComments())
                    .deniedPolitics(deniedPolitics)  // Asignar las políticas rechazadas
                    .user(user)
                    .build();
            Dictum dictumRegistered = dictumRepository.save(dictum);

            // Si no se han rechazado políticas, crear y guardar un nuevo crédito
            if (notDeniedPolitics) {
                Credit credit = Credit.builder()
                        .startDate(new Date())
                        .status(CreditStatus.ACTIVE)
                        .creditApplication(creditApplication)
                        .build();
                Credit creditRegistered = creditRepository.save(credit);

                // Asignar el crédito a la solicitud de crédito
                creditApplication.setCredit(creditRegistered);
            }

            // Actualizar el estado de la solicitud de crédito y asociar el dictamen
            creditApplication.setDictum(dictumRegistered);
            creditApplication.setStatus(notDeniedPolitics ? CreditApplicationStatus.ACTIVE : CreditApplicationStatus.DENIED);
            creditApplicationRepository.save(creditApplication);
        } else {
            // Manejar el caso en que no se encuentre la solicitud de crédito o el usuario
            throw new IllegalArgumentException("Credit application or user not found");
        }
    }



    private static Date getCurrentDate() {
        java.util.Date utilDate = new java.util.Date();

        return new Date(utilDate.getTime());
    }

    public CreditByIdResponse getCreditsById(int creditId) {

        Credit credit = creditRepository.findById(creditId).get();
        CreditType creditType = credit.getCreditApplication().getSelectedCredit();
        CreditApplication creditApplication = credit.getCreditApplication();
        Client client = creditApplication.getCreditApplicant();


        return CreditByIdResponse.builder()
                .creditNumber(creditId)
                .amountBorrowed("80,000")
                .clientName(client.getName() + client.getLastname())
                .term(creditType.getTerm())
                .creditType(creditType.getDescription())
                .build();
    }
}
