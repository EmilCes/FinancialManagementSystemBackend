package com.softdev.fmsb.creditType.infraestructure;

import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.creditType.model.CreditState;
import com.softdev.fmsb.creditType.model.CreditType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditTypeRepository extends JpaRepository<CreditType, Integer> {
    List<CreditType> getCreditTypesByState (CreditState creditState);

    CreditType getCreditTypeByCreditTypeId (Integer id);
}
