package com.softdev.fmsb.credit.infraestructure;

import com.softdev.fmsb.auth.model.Token;
import com.softdev.fmsb.client.model.Workplace;
import com.softdev.fmsb.credit.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

    List<Credit> getCreditByCreditApplication_CreditApplicant_Rfc(String rfc);

    @Query("SELECT c FROM Credit c WHERE c.startDate = (SELECT MIN(c2.startDate) FROM Credit c2)")
    Optional<Credit> findOldestCredit();

    List<Credit> getCreditsByEndDateIsNull();
}
