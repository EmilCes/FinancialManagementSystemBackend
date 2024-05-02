package com.softdev.fmsb.creditType.infraestructure;

import com.softdev.fmsb.creditType.application.CreditTypeService;
import com.softdev.fmsb.creditType.model.CreditType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit-type")
@RequiredArgsConstructor
public class CreditTypeController {

    private final CreditTypeService creditTypeService;

    @GetMapping("/")
    public ResponseEntity<?> getCreditsTypes() {
        try{
            List<CreditType> response = creditTypeService.getCredits();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getActive")
    public ResponseEntity<?> getCreditsTypesByActiveStatus (){
        try {
            List<CreditType> response = creditTypeService.getActiveCreditTypes();
            return ResponseEntity.ok(response);
        } catch (Exception e){
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCredit(@RequestBody CreditType creditType){
        try{
            System.out.println("hola");
            creditTypeService.registerCredit(creditType);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            //TODO: Log exception
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyCredit(@RequestBody CreditType creditType){
        try{
            creditTypeService.modifyCredit(creditType);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            //TODO: Log exception
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
            return ResponseEntity.badRequest().build();
        }
    }
}
