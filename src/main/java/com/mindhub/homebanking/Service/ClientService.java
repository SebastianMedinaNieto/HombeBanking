package com.mindhub.homebanking.Service;


import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface ClientService {

    Set<ClientDTO> getClientsDTO();

    ClientDTO getClientDTO(long id);

    Client getByEmail(String email);

    ClientDTO getCurrentDTO(Authentication authentication);

    Client  getCurrent(Authentication authentication);

    void saveClient(Client client);

}
