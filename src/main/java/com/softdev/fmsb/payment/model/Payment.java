package com.softdev.fmsb.payment.model;

import com.softdev.fmsb.credit.model.Credit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue
    private Integer paymentId;
    private String folio;
    private Date paymentDate;
    private float amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditId", referencedColumnName = "creditId")
    private Credit credit;
}
