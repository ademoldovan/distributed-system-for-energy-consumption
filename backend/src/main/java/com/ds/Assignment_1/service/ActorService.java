package com.ds.Assignment_1.service;

import com.ds.Assignment_1.model.Actor;
import com.ds.Assignment_1.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public  Actor getActor(String username, String password) {
        return actorRepository.findActorByUsernameAndPassword(username, password);
    }

    public void createActor(Actor actor) {
        actorRepository.save(actor);
    }

    public Actor findActorByUsername(String username) {
        return actorRepository.findActorByUsername(username);
    }
}
