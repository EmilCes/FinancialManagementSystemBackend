package com.softdev.fmsb.credit.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.softdev.fmsb.auth.model.User;
import com.softdev.fmsb.creditApplication.model.CreditApplication;
import com.softdev.fmsb.politics.model.Politic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Dictums")
public class Dictum {

    @Id
    @GeneratedValue
    private Integer dictumId;

    private String comments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "dictum")
    @JsonManagedReference
    private List<Politic> deniedPolitics;

    @OneToOne
    @JoinColumn(name = "creditApplicationId")
    private CreditApplication creditApplication;

}
