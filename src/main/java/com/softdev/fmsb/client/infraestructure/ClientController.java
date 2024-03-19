package com.softdev.fmsb.client.infraestructure;

import com.softdev.fmsb.auth.infraestructure.dto.RegisterRequest;
import com.softdev.fmsb.client.application.ClientService;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceRequest;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceResponse;
import com.softdev.fmsb.client.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<?> getClients() {
        try{
            List<Client> response = clientService.getClients();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody Client client){
        try{
            clientService.registerClient(client);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/verify-existence")
    public ResponseEntity<?> verifyClientExistence(@RequestBody VerifyClientExistenceRequest request) {
        try {
            VerifyClientExistenceResponse verifyClientExistenceResponse = clientService.verifyClientExistence(request);
            return ResponseEntity.ok(verifyClientExistenceResponse);
        } catch (Exception e) {
            //TODO: Log exception
            return ResponseEntity.badRequest().build();
        }
    }

}
