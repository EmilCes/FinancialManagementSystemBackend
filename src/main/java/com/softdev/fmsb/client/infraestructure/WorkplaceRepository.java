package com.softdev.fmsb.client.infraestructure;

import com.softdev.fmsb.client.model.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkplaceRepository extends JpaRepository<Workplace, Integer> {
}
