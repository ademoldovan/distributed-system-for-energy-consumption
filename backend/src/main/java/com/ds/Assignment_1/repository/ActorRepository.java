package com.ds.Assignment_1.repository;

import com.ds.Assignment_1.model.Actor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends CrudRepository<Actor, String> {
    Actor findActorByUsernameAndPassword(String username, String password);
    Actor findActorByUsername(String username);
    Actor save(Actor actor);
}
