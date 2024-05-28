package com.softdev.fmsb.credit.infraestructure;

import com.softdev.fmsb.auth.model.Token;
import com.softdev.fmsb.client.model.Workplace;
import com.softdev.fmsb.credit.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

    List<Credit> getCreditByCreditApplication_CreditApplicant_Rfc(String rfc);

}
