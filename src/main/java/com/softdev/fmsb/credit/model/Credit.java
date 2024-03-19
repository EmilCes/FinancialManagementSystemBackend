package com.softdev.fmsb.credit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String description;
    //private String creditId;
    //private String creditId;
    //private String creditId;

}
