package com.softdev.fmsb.credit.infraestructure;

import com.softdev.fmsb.client.application.ClientService;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceRequest;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceResponse;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.application.CreditService;
import com.softdev.fmsb.credit.model.Credit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @GetMapping("/")
    public ResponseEntity<?> getCredits() {
        try{
            List<Credit> response = creditService.getCredits();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody Credit credit){
        try{
            creditService.registerCredit(credit);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }
}
