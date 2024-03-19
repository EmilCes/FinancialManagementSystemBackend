package com.softdev.fmsb.client.infraestructure;

import com.softdev.fmsb.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsClientByRfc(String rfc);
}
