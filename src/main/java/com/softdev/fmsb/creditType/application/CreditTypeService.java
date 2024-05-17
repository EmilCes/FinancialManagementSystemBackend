package com.softdev.fmsb.creditType.application;

import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.creditType.infraestructure.CreditTypeRepository;
import com.softdev.fmsb.creditType.model.CreditState;
import com.softdev.fmsb.creditType.model.CreditType;
import com.softdev.fmsb.politics.model.Politic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void modifyCredit(CreditType creditType){

        Optional<CreditType> optionalCreditType = creditTypeRepository.findById(creditType.getCreditTypeId());

        if (optionalCreditType.isPresent()) {
            CreditType existingCreditType = optionalCreditType.get();
            BeanUtils.copyProperties(creditType, existingCreditType, "creditTypeId");
            creditTypeRepository.save(existingCreditType);
        }
    }


    public List<CreditType> getActiveCreditTypes () {return creditTypeRepository.getCreditTypesByState(CreditState.Activo);}

}
