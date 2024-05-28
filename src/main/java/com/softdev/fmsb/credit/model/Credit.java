package com.softdev.fmsb.credit.model;

import com.softdev.fmsb.creditApplication.model.CreditApplication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date startDate;
    private Date endDate;
    private CreditStatus status;
    private float leftAmount;

    @OneToOne
    @JoinColumn(name = "creditApplicationId")
    private CreditApplication creditApplication;

}
