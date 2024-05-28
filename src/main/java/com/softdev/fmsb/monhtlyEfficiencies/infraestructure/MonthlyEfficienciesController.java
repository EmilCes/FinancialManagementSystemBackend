package com.softdev.fmsb.monhtlyEfficiencies.infraestructure;

import com.softdev.fmsb.monhtlyEfficiencies.application.MonthlyEfficienciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/efficiencies")
@RequiredArgsConstructor
public class MonthlyEfficienciesController {
    private final MonthlyEfficienciesService service;

    @GetMapping("/year")
    public ResponseEntity<?> getStartYear(){
        try{
            return ResponseEntity.ok(service.getOldestCreditYear());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getMonthsEfficiencies(@RequestParam int year){
        try{
            return ResponseEntity.ok(service.getMonthlyEfficiencies(year));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
