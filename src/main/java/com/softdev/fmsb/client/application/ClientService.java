package com.softdev.fmsb.client.application;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.softdev.fmsb.client.infraestructure.BankAccountRepository;
import com.softdev.fmsb.client.infraestructure.ClientRepository;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceRequest;
import com.softdev.fmsb.client.infraestructure.dto.VerifyClientExistenceResponse;
import com.softdev.fmsb.client.model.BankAccount;
import com.softdev.fmsb.client.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    public Client getClientById(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    public void registerClient(Client client) {
        if (!clientRepository.existsClientByRfc(client.getRfc())){
            clientRepository.save(client);
        }
    }

    public VerifyClientExistenceResponse verifyClientExistence(VerifyClientExistenceRequest verifyClientExistenceRequest) {
        boolean isClientRegistered = clientRepository.existsClientByRfc(verifyClientExistenceRequest.getClientRfc());
        return new VerifyClientExistenceResponse(isClientRegistered);
    }


    public Client updateClient(Integer id, Client clientUpdated) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            Client existedClient = optionalClient.get();
            BeanUtils.copyProperties(clientUpdated, existedClient, "clientId", "rfc");
            clientRepository.save(existedClient);
        }

        return optionalClient.orElse(null);
    }
}
