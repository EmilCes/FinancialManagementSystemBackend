package com.softdev.fmsb.creditApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.model.Credit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Ref;
import java.util.Date;
import java.util.List;


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
    private CreditApplicationStatus status;

    @Temporal(TemporalType.DATE)
    private Date dateOfApplication;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "creditApplication")
    @JsonManagedReference
    private List<Reference> references;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditId", referencedColumnName = "creditId")
    private Credit selectedCredit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Client creditApplicant;

}
