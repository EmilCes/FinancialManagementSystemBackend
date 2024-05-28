package com.softdev.fmsb.payment.application;

import com.softdev.fmsb.client.infraestructure.ClientRepository;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.infraestructure.CreditRepository;
import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.credit.model.CreditStatus;
import com.softdev.fmsb.creditApplication.infraestructure.CreditApplicationRepository;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.creditType.model.CreditType;
import com.softdev.fmsb.payment.infraestructure.PaymentRepository;
import com.softdev.fmsb.payment.model.CreatePaymentRequest;
import com.softdev.fmsb.payment.model.Payment;
import com.softdev.fmsb.payment.model.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final CreditApplicationRepository creditApplicationRepository;

    public PaymentResponse paymentResponse (String rfc){
        Credit paymentCredit = getCredit(rfc);
        Client client = clientRepository.findClientByRfc(rfc).get();
        CreditApplication creditApplication = creditApplicationRepository.getCreditApplicationByCreditApplicant(client);
        CreditType creditType = creditApplication.getSelectedCredit();

        Date startDateAsDate = paymentCredit.getStartDate();
        LocalDate startDate = startDateAsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        long remainingMonths = ((long) creditType.getTerm()) - ChronoUnit.MONTHS.between(startDate, currentDate);

        LocalDate deadline = null;
        LocalDate startTermDate = null;

        switch (creditType.getTermType()){
            case Mensual:
                deadline = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), startDate.getDayOfMonth()).plusMonths(1);
                startTermDate = deadline.minusMonths(1);
                break;
            case Semanal:
                long daysBetween = ChronoUnit.DAYS.between(startDate, currentDate);
                long daysToMultiply = daysBetween / 7;
                int roundedValue = (int) Math.floor(daysToMultiply);
                int daysToAdd = roundedValue * 7;
                deadline = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), startDate.getDayOfMonth()).plusDays(daysToAdd + 7);
                startTermDate = deadline.minusDays(7);
                break;
            case Quincenal:
                daysBetween = ChronoUnit.DAYS.between(startDate, currentDate);
                daysToMultiply = daysBetween / 15;
                roundedValue = (int) Math.floor(daysToMultiply);
                daysToAdd = roundedValue * 15;
                deadline = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), startDate.getDayOfMonth()).plusDays(daysToAdd + 15);
                startTermDate = deadline.minusDays(15);
                break;
        }
        Date deadlineDateAsDate = Date.from(deadline.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startTermDateAsDate = Date.from(startTermDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        float amountForNoInterest = creditType.getAmount() / creditType.getTerm();
        float amountAdded = 0;
        List<Payment> payments = paymentRepository.findByPaymentDateBetweenAndCredit(startTermDateAsDate, deadlineDateAsDate, paymentCredit);

        for (Payment payment: payments) {
            amountAdded = amountAdded + payment.getAmount();
        }

        amountForNoInterest = amountForNoInterest - amountAdded;

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .clientName(client.getName() + " " + client.getLastname() + " " + client.getSurname())
                .remainingMonths(remainingMonths)
                .monthDeadlineDate(deadlineDateAsDate)
                .pendingAmount(paymentCredit.getLeftAmount())
                .amountForNoInterest(amountForNoInterest)
                .termType(creditType.getTermType())
                .build();

        return paymentResponse;
    }

    public boolean paymentExist (String folio) {
        return paymentRepository.existsPaymentByFolio(folio);
    }

    private Credit getCredit(String rfc) {
        List<Credit> credits = creditRepository.getCreditByCreditApplication_CreditApplicant_Rfc(rfc);
        Credit creditToReturn = null;

        if(!credits.isEmpty()){
            for (Credit credit: credits) {
                if (credit.getStatus() == CreditStatus.ACTIVE){
                    creditToReturn = credit;
                    break;
                }
                else {
                    creditToReturn.setCreditId(0);
                }
            }
        }

        return creditToReturn;
    }

    public void createPayment (CreatePaymentRequest createPaymentRequest){
        Credit paymentCredit = getCredit(createPaymentRequest.getRfc());

        if(paymentCredit.getCreditId() != 0){
            Payment payment = Payment.builder()
                    .paymentDate(new Date())
                    .amount(createPaymentRequest.getAmount())
                    .folio(createPaymentRequest.getFolio())
                    .credit(paymentCredit)
                    .build();

            paymentCredit.setLeftAmount(paymentCredit.getLeftAmount() - payment.getAmount());

            if (paymentCredit.getLeftAmount() <= 0){
                paymentCredit.setStatus(CreditStatus.FINISHED);
            }

            paymentRepository.save(payment);
            creditRepository.save(paymentCredit);
        }
        else {
            throw new IllegalArgumentException("Credit or user not found");
        }

    }

}
