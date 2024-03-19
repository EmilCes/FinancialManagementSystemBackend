package com.softdev.fmsb.client.application;

import com.softdev.fmsb.client.infraestructure.BankAccountRepository;
import com.softdev.fmsb.client.infraestructure.ClientRepository;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceRequest;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceResponse;
import com.softdev.fmsb.client.model.BankAccount;
import com.softdev.fmsb.client.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void registerClient(Client client) {
        clientRepository.save(client);
    }

    public VerifyClientExistenceResponse verifyClientExistence(VerifyClientExistenceRequest verifyClientExistenceRequest) {
        boolean isClientRegistered = clientRepository.existsClientByRfc(verifyClientExistenceRequest.getClientRfc());
        return new VerifyClientExistenceResponse(isClientRegistered);
    }
}
