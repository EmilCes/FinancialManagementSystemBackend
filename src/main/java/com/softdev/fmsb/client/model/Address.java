package com.softdev.fmsb.client.model;

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
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue
    private Integer addressId;
    private String street;
    private String neighborhood;
    private Integer exteriorNumber;
    private Integer interiorNumber;
    private String postalCode;
    private String municipality;
    private String state;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;
}
