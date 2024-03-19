package com.softdev.fmsb.creditApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CreditApplications")
public class CreditApplication {
    @Id
    @GeneratedValue
    private Integer creditApplicationId;
    private String clientRfc;
}
