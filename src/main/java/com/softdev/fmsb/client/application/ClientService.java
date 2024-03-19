package com.softdev.fmsb.client.application;

import com.softdev.fmsb.client.infraestructure.BankAccountRepository;
import com.softdev.fmsb.client.infraestructure.ClientRepository;
import com.softdev.fmsb.client.model.BankAccount;
import com.softdev.fmsb.client.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void registerClient(Client client) {
        clientRepository.save(client);
        /*Client client1 = clientRepository.save(client);
        BankAccount bankAccount = new BankAccount().builder()
                .client(client1)
                .bankName(client.getBankAccounts().get(0).getBankName())
                .clabe(client.getBankAccounts().get(0).getClabe())
                .build();
        BankAccount bankAccount2 = new BankAccount().builder()
                .client(client1)
                .bankName(client.getBankAccounts().get(2).getBankName())
                .clabe(client.getBankAccounts().get(2).getClabe())
                .build();

        bankAccountRepository.save(bankAccount);
        bankAccountRepository.save(bankAccount2);*/
    }
}
