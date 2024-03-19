package com.softdev.fmsb.creditApplication.infraestructure;

import com.softdev.fmsb.creditApplication.model.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Integer> {
}
