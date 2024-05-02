package com.softdev.fmsb.credit.infraestructure;

import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.application.CreditService;
import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.credit.model.CreditResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @GetMapping("/")
    public ResponseEntity<?> getCredits() {
        try{
            List<CreditResponse> response = creditService.getCredits();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{rfc}")
    public ResponseEntity<?> getClientByRfc(@PathVariable String rfc) {
        try{
            List<Credit> response = creditService.getCreditsByRfc(rfc);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

}
