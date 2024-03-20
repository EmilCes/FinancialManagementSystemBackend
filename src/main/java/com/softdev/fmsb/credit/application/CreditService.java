package com.softdev.fmsb.credit.application;

import com.softdev.fmsb.credit.infraestructure.CreditRpository;
import com.softdev.fmsb.credit.model.Credit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRpository creditRpository;

    public List<Credit> getCredits() {
        return creditRpository.findAll();
    }

    public void registerCredit(Credit credit){
        creditRpository.save(credit);
    }

}
