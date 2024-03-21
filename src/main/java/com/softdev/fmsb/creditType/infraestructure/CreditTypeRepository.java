package com.softdev.fmsb.creditType.infraestructure;

import com.softdev.fmsb.creditType.model.CreditType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditTypeRepository extends JpaRepository<CreditType, Integer> {
}
