package com.softdev.fmsb.creditApplication.infraestructure;

import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.creditApplication.model.CreditApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Integer> {
    boolean existsCreditApplicationByCreditApplicantAndStatus (Client client, CreditApplicationStatus status);
    CreditApplication getCreditApplicationByCreditApplicant (Client client);
    List<CreditApplication> findByCreditIsNotNull();

}
