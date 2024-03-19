package com.softdev.fmsb.client.infraestructure;

import com.softdev.fmsb.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
