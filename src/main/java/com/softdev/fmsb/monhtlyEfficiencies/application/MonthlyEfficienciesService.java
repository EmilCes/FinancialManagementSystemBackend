package com.softdev.fmsb.monhtlyEfficiencies.application;

import com.softdev.fmsb.credit.infraestructure.CreditRepository;
import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.creditType.model.CreditType;
import com.softdev.fmsb.monhtlyEfficiencies.model.MonthResponse;
import com.softdev.fmsb.payment.infraestructure.PaymentRepository;
import com.softdev.fmsb.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthlyEfficienciesService {
    private final PaymentRepository paymentRepository;
    private final CreditRepository creditRepository;

    public List<MonthResponse> getMonthlyEfficiencies (int year){

        Month january = Month.JANUARY;
        List<Credit> creditsList = creditRepository.getCreditsByEndDateIsNull();
        List<MonthResponse> monthResponses = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            MonthResponse monthResponse = new MonthResponse();
            monthResponse.setMonth(january.plus(i));
            monthResponses.add(monthResponse);
        }

        if (!creditsList.isEmpty()){
            for (Credit credit: creditsList) {
                double totalCreditAmount = getTotalAmount(credit.getCreditApplication().getSelectedCredit());
                int term = credit.getCreditApplication().getSelectedCredit().getTerm();
                int termsLeftThisYear;

                if(differenceInMonths(credit.getStartDate(), year) >= 0){
                    termsLeftThisYear = term - differenceInMonths(credit.getStartDate(), year);

                }
                else {
                    termsLeftThisYear = term;
                }

                for (int i = 0; i < termsLeftThisYear && i < 12; i++) {

                    if(credit.getStartDate().getMonth() < i + 1 && credit.getStartDate().getYear() + 1900 == year){
                        monthResponses.get(i).setActualAmount(monthResponses.get(i).getActualAmount() + getActualAmount(credit, january.plus(i), year));
                        monthResponses.get(i).setExpectedAmount(monthResponses.get(i).getExpectedAmount() + (totalCreditAmount / term));
                    }
                    if(credit.getStartDate().getYear() + 1900 < year){
                        monthResponses.get(i).setActualAmount(monthResponses.get(i).getActualAmount() + getActualAmount(credit, january.plus(i), year));
                        monthResponses.get(i).setExpectedAmount(monthResponses.get(i).getExpectedAmount() + (totalCreditAmount / term));
                    }
                }
            }
        }

        return monthResponses;
    }

    private double getActualAmount(Credit credit, Month month, int year){
        double amountAdded = 0;
        LocalDate startDateOfMonth = LocalDate.of(year, month, 1);
        Date startDateOfMonthAsDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endDateOfMonth = startDateOfMonth.withDayOfMonth(startDateOfMonth.lengthOfMonth());
        Date endDateOfMonthAsDate = Date.from(endDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Payment> payments = paymentRepository.findByPaymentDateBetweenAndCredit(startDateOfMonthAsDate, endDateOfMonthAsDate, credit);

        for (Payment payment: payments) {
            amountAdded = amountAdded + payment.getAmount();
        }

        return amountAdded;
    }

    private int differenceInMonths(Date startDate, int year) {
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = LocalDate.of(year, 1, 1);

        Period period = Period.between(localStartDate, endDate);

        int years = period.getYears();
        int months = period.getMonths();

        int totalMonths = years * 12 + months;

        return totalMonths;
    }

    private float getTotalAmount(CreditType creditType){
        float ivaAmount = creditType.getAmount() * creditType.getIva();
        float interestAmount = creditType.getAmount() * creditType.getInterestRate();
        float totalAmount = creditType.getAmount() + ivaAmount + interestAmount;

        return totalAmount;
    }

    public int getOldestCreditYear() {
        Optional<Credit> oldestCreditOptional = creditRepository.findOldestCredit();
        int year;

        if (oldestCreditOptional.isPresent()){
            Credit oldestCredit = oldestCreditOptional.get();
            year = oldestCredit.getStartDate().getYear();
        }
        else{
            year = -1;
        }

        return year + 1900;
    }

}
