package com.softdev.fmsb.credit.model;

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
@Table(name = "Credits")
public class Credit {

    @Id
    @GeneratedValue
    private Integer creditId;


    // Here your attributes

    @OneToOne
    @JoinColumn(name = "creditApplicationId")
    private CreditApplication creditApplication;

}
