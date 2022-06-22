package com.mindhub.homebanking.Controller;
import com.mindhub.homebanking.Configurations.WebAuthentication;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Utils.Utility;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.security.core.Authentication;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    String accountNumber = String.valueOf(Utility.getRandomNumber(00000000, 99999999));

    @GetMapping("/clients")
   public Set<ClientDTO> getAll() {
        return clientService.getClientsDTO();

  }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);

    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {



        if ( firstName.isEmpty()) {
            return new ResponseEntity<>("Missing First Name", HttpStatus.FORBIDDEN);
        }
        if(lastName.isEmpty()){
            return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
        }
        if(email.isEmpty()){
            return new ResponseEntity<>("Missing Email", HttpStatus.FORBIDDEN);
        }
        if(password.isEmpty()){
            return new ResponseEntity<>("Missing Password", HttpStatus.FORBIDDEN);
        }
        if (clientService.getByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);

        Account account = new Account(client, "VIN"+accountNumber, LocalDateTime.now(), 0, AccountType.CORRIENTE);
        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @GetMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication) {

         return clientService.getCurrentDTO(authentication);
    }

}
