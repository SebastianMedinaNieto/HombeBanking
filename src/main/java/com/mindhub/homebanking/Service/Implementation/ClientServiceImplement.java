package com.mindhub.homebanking.Service.Implementation;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.ClientLoanDTO;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {


    @Autowired
    ClientRepository clientRepository;


    @Override
    public Set<ClientDTO> getClientsDTO() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toSet());
    }

    @Override
    public ClientDTO getClientDTO(long id) {
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientDTO getCurrentDTO(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

    @Override
    public Client getCurrent(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);

    }
}
