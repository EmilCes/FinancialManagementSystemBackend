package com.softdev.fmsb.politics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softdev.fmsb.creditType.model.CreditType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Politics")
public class Politic {

    @Id
    @GeneratedValue
    private Integer politicId;
    private String name;
    private String description;
    private String state;

    @ManyToMany(mappedBy = "politics")
    @JsonIgnoreProperties("politics")
    private List<CreditType> creditTypes;
}
