package com.softdev.fmsb.client.infraestructure;


import com.softdev.fmsb.client.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
