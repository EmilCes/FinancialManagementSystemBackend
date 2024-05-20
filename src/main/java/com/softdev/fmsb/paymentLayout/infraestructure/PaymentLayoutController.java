package com.softdev.fmsb.paymentLayout.infraestructure;

import com.softdev.fmsb.client.application.ClientService;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.paymentLayout.application.PaymentLayoutService;
import com.softdev.fmsb.paymentLayout.model.PaymentLayout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-layout")
@RequiredArgsConstructor
public class PaymentLayoutController {

    private final PaymentLayoutService paymentLayoutService;

    @GetMapping("/")
    public ResponseEntity<?> getPaymentLayouts() {
        try{
            List<PaymentLayout> response = paymentLayoutService.getPaymentLayouts();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

}
