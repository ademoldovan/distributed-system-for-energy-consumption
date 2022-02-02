package com.ds.Assignment_1.controller;

import com.ds.Assignment_1.dto.*;
import com.ds.Assignment_1.model.Actor;
import com.ds.Assignment_1.model.Client;
import com.ds.Assignment_1.repository.ActorRepository;
import com.ds.Assignment_1.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Client")
@CrossOrigin
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/getClients")
    public ResponseEntity getClients(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
    }
    @GetMapping("/getClientById")
    public ResponseEntity getClientById(Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getById(id));
    }
    @PostMapping("/CreateClient")
    public void createClient(@RequestBody CreateClientDto client){
        Actor actor = new Actor(client.getEmail(), client.getPassword(), "client");
        Client user = new Client(client.getFirstName(),client.getLastName(),client.getBirthDate(),client.getAddress(),actor);
        clientService.createClient(user);
    }
    @PostMapping("/DeleteClient")
    public void deleteClient(@RequestBody DeleteDto deleteDto){
        clientService.deleteById(deleteDto.getId());
    }

    @PostMapping("/UpdateClient")
    public void updateClient(@RequestBody UpdateClientDto client){
        Client c_username = clientService.getById(client.getId());
        Client user = new Client(client.getId(), client.getFirstName(),client.getLastName(),client.getBirthDate(),client.getAddress(),c_username.getUser());
        clientService.createClient(user);
    }
}