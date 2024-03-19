package com.softdev.fmsb.politics.infraestructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softdev.fmsb.auth.application.AuthenticationService;
import com.softdev.fmsb.auth.infraestructure.dto.AuthenticationRequest;
import com.softdev.fmsb.auth.infraestructure.dto.RegisterRequest;
import com.softdev.fmsb.auth.infraestructure.dto.VerificationRequest;
import com.softdev.fmsb.politics.application.PoliticsService;
import com.softdev.fmsb.politics.infraestructure.dto.PoliticRequest;
import com.softdev.fmsb.politics.model.Politic;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/politics")
@RequiredArgsConstructor
public class PoliticsController {

    private final PoliticsService politicsService;

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<?> register(@RequestBody PoliticRequest request){
        politicsService.register(request);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/modify")
    @PermitAll
    public ResponseEntity<?> modify(@RequestBody PoliticRequest request){
        politicsService.modify(request);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/")
    @PermitAll
    public ResponseEntity<List<Politic>> getPolitics() {
        return ResponseEntity.ok(politicsService.getPolitics());
    }
}

