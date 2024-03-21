package com.softdev.fmsb.creditType.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softdev.fmsb.politics.model.Politic;
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
@Table(name = "CreditTypes")
public class CreditType {

    @Id
    @GeneratedValue
    private Integer creditTypeId;
    private String description;
    private float interestRate;
    private CreditState state;
    private String term;
    private float iva;

    @ManyToMany
    @JoinTable(
            name = "CreditType_Politic",
            joinColumns = @JoinColumn(name = "creditTypeId"),
            inverseJoinColumns = @JoinColumn(name = "politicId")
    )
    @JsonIgnoreProperties("creditTypes")
    private List<Politic> politics;

}
