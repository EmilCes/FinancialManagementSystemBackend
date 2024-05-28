package com.softdev.fmsb.payment.infraestructure;

import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsPaymentByFolio(String folio);

    List<Payment> findByPaymentDateBetweenAndCredit(Date startDate, Date endDate, Credit credit);
}
