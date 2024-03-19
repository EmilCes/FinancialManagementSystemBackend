package com.softdev.fmsb.politics.infraestructure;

import com.softdev.fmsb.auth.model.User;
import com.softdev.fmsb.politics.model.Politic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliticRepository extends JpaRepository<Politic, Integer> {
}
