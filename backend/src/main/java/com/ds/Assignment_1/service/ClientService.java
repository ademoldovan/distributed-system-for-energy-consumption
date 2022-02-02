package com.ds.Assignment_1.service;

import com.ds.Assignment_1.dto.ReadClientDto;
import com.ds.Assignment_1.model.Actor;
import com.ds.Assignment_1.model.Client;
import com.ds.Assignment_1.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client getById(Long id) {
        return clientRepository.findClientById(id);
    }

    public Client findClientByUser(Actor user) {
        return clientRepository.findClientByUser(user);
    }

    public List<ReadClientDto> getAll() {
        List<Client> clients = clientRepository.findAll();
        List<ReadClientDto> readClientDtos = clients.stream().map(client ->
        {
            return new ReadClientDto(
                    client.getId(),
                    client.getLastName(),
                    client.getFirstName(),
                    client.getBirthDate(),
                    client.getAddress(),
                    client.getUser().getUsername()
            );
        }).collect(Collectors.toList());
        return readClientDtos;
    }

    public void createClient(Client client) {
        clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }


}
