package com.softdev.fmsb.creditApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softdev.fmsb.client.model.Client;
import com.softdev.fmsb.credit.model.Credit;
import com.softdev.fmsb.credit.model.Dictum;
import com.softdev.fmsb.creditType.model.CreditType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String identificationPdfPath;
    private String proofOfAddressPdfPath;
    private String proofOfIncomePdfPath;
    private int idCreditType;

    @Temporal(TemporalType.DATE)
    private Date dateOfApplication;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dictumId", referencedColumnName = "dictumId")
    private Dictum dictum;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "creditId", referencedColumnName = "creditId")
    private Credit credit;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "creditApplication")
    private List<Reference> references;

    // Evitar que se serialice la referencia a CreditType
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditTypeId", referencedColumnName = "creditTypeId")
    private CreditType selectedCredit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Client creditApplicant;

}
