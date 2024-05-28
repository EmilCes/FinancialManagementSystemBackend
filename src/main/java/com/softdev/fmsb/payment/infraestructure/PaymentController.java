package com.softdev.fmsb.payment.infraestructure;

import com.softdev.fmsb.payment.application.PaymentService;
import com.softdev.fmsb.payment.model.CreatePaymentRequest;
import com.softdev.fmsb.payment.model.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/credit")
    public ResponseEntity<?> getCreditInfo(String rfc){
        try{
            PaymentResponse paymentResponse = paymentService.paymentResponse(rfc);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/existance")
    public ResponseEntity<?> paymentExists(@RequestParam String folio){
        try{
            boolean paymentExists = paymentService.paymentExist(folio);
            return ResponseEntity.ok(paymentExists);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/savePayment")
    public ResponseEntity<?> savePayment(@RequestBody CreatePaymentRequest createPaymentRequest){
        try{
            if(!paymentService.paymentExist(createPaymentRequest.getFolio())){
                paymentService.createPayment(createPaymentRequest);
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
