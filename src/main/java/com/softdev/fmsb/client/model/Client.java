package com.softdev.fmsb.client.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Clients")
public class Client {

    @Id
    @GeneratedValue
    private Integer clientId;
    private String name;
    private String lastname;
    private String surname;
    private String phoneNumber;
    private String email;
    private String rfc;
    private float monthlySalary;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workplaceId", referencedColumnName = "workplaceId")
    private Workplace workplace;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BankAccount> bankAccounts;

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name +
                '}';
    }
}
