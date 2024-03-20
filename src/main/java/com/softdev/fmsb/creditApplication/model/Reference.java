package com.softdev.fmsb.creditApplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softdev.fmsb.credit.model.Credit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Credit_References")
public class Reference {

    @Id
    @GeneratedValue
    private Integer referenceId;
    private String name;
    private String firstLastname;
    private String secondLastname;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "creditApplicationId")
    @JsonBackReference
    private CreditApplication creditApplication;
}
