package com.softdev.fmsb.credit.infraestructure;

import com.softdev.fmsb.credit.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRpository extends JpaRepository<Credit, Integer> {
}
