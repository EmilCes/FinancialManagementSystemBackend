package com.softdev.fmsb.creditApplication.infraestructure;

import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.creditApplication.application.CreditApplicationService;
import com.softdev.fmsb.creditApplication.infraestructure.dto.CreditAplicationRequest;
import com.softdev.fmsb.creditApplication.infraestructure.dto.VerifyClientRequest;
import com.softdev.fmsb.creditApplication.infraestructure.dto.VerifyRegularClientResponse;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.creditApplication.model.CreditApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/creditApplication")
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @GetMapping("/")
    public ResponseEntity<?> GetAllCreditsApplications(){
        try{
            List<CreditApplication> response = creditApplicationService.GetAllCreditApplication();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/regularClient")
    public ResponseEntity<?> GetIsRegularClient(VerifyClientRequest verifyClientRequest){
        try{
            VerifyRegularClientResponse verifyRegularClientResponse = creditApplicationService.verifyRegularClientResponse(verifyClientRequest);
            return ResponseEntity.ok(verifyRegularClientResponse);
        } catch (Exception e){
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/applicate")
    public ResponseEntity<?> ApplicateForCredit(@RequestBody CreditApplication creditApplication){
        Date actualDate = new Date();

        creditApplication.setDateOfApplication(actualDate);
        creditApplication.setStatus(CreditApplicationStatus.ON_HOLD_FOR_DICTAMEN);

        try{
            creditApplicationService.CreateCreditApplication(creditApplication);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            //TODO: Log exception
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}


