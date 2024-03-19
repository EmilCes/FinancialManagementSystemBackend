package com.softdev.fmsb.client.infraestructure;

import com.softdev.fmsb.client.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
}
