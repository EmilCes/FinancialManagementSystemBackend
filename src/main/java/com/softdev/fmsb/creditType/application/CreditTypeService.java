package com.softdev.fmsb.creditType.application;

import com.softdev.fmsb.creditType.infraestructure.CreditTypeRepository;
import com.softdev.fmsb.creditType.model.CreditState;
import com.softdev.fmsb.creditType.model.CreditType;
import com.softdev.fmsb.politics.model.Politic;
import lombok.RequiredArgsConstructor;
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
        Optional<CreditType> creditTypeInBD= creditTypeRepository.findById(creditType.getCreditTypeId());
        creditTypeInBD.get().setDescription(creditType.getDescription());
        creditTypeInBD.get().setInterestRate(creditType.getInterestRate());
        creditTypeInBD.get().setState(creditType.getState());
        creditTypeInBD.get().setTerm(creditType.getTerm());
        creditTypeInBD.get().setIva(creditType.getIva());
        creditTypeInBD.get().setPolitics(creditType.getPolitics());
        creditTypeInBD.get().setAmount(creditType.getAmount());
        creditTypeInBD.get().setTermType(creditType.getTermType());
        creditTypeRepository.save(creditTypeInBD.get());
    }


    public List<CreditType> getActiveCreditTypes () {return creditTypeRepository.getCreditTypesByState(CreditState.Activo);}

}
