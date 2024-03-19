package com.softdev.fmsb.politics.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
}
