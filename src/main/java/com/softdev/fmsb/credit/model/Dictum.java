package com.softdev.fmsb.credit.model;

import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Dictums")
public class Dictum {

    @Id
    @GeneratedValue
    private Integer dictumId;

    // Here your attributes

    @OneToOne
    @JoinColumn(name = "creditApplicationId")
    private CreditApplication creditApplication;

}
