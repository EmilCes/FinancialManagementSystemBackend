package com.softdev.fmsb.client.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "BankAccounts")
public class BankAccount {

    @Id
    @GeneratedValue
    private Integer bankAccountId;
    private String clabe;
    private String bankName;

    @ManyToOne
    @JoinColumn(name = "clientId")
    @JsonBackReference
    private Client client;
}
