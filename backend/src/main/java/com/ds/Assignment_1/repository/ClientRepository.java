package com.ds.Assignment_1.repository;

import com.ds.Assignment_1.model.Actor;
import com.ds.Assignment_1.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long>{
    Client findClientById(Long id);
    List<Client> findAll();
    Client save(Client client);
    void deleteById(Long id);
    Client findClientByUser(Actor user);

}
