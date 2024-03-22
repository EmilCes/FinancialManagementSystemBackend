package com.softdev.fmsb.creditType.application;

import com.softdev.fmsb.creditType.infraestructure.CreditTypeRepository;
import com.softdev.fmsb.creditType.model.CreditState;
import com.softdev.fmsb.creditType.model.CreditType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditTypeService {

    private final CreditTypeRepository creditTypeRepository;

    public List<CreditType> getCredits() {
        return creditTypeRepository.findAll();
    }

    public void registerCredit(CreditType creditType){
        creditTypeRepository.save(creditType);
    }

    public List<CreditType> getActiveCreditTypes () {return creditTypeRepository.getCreditTypesByState(CreditState.Activo);}

}
