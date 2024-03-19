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
@Table(name = "Workplaces")
public class Workplace {

    @Id
    @GeneratedValue
    private Integer workplaceId;
    private String name;
    private String phoneNumber;
    private String email;
    private String rfc;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;
}
