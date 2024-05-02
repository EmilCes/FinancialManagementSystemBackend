package com.softdev.fmsb.credit.infraestructure;

import com.softdev.fmsb.client.model.Workplace;
import com.softdev.fmsb.credit.model.Dictum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictumRepository extends JpaRepository<Dictum, Integer> {
}
