package com.ds.Assignment_1.controller;

import com.ds.Assignment_1.dto.CreateClientDto;
import com.ds.Assignment_1.dto.LogInDto;
import com.ds.Assignment_1.model.Actor;
import com.ds.Assignment_1.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/User")
@CrossOrigin
public class ActorController {
    @Autowired
    private ActorService actorService;
    String role;

    @PostMapping("/LogIn")
    public void logIn(@RequestBody LogInDto user){
         Actor actor = actorService.getActor(user.getUsername(), user.getPassword());
         if(actor != null){
             this.role = actor.getRole();
         }else{
             this.role = "null";
         }
    }
    @PostMapping("/CreateUser")
    public void createUser(@RequestBody CreateClientDto client){
        Actor actor = new Actor(client.getEmail(), client.getPassword(), "client");
        actorService.createActor(actor);
    }
    @GetMapping("/LogInResponse")
    public ResponseEntity logInResponse(){
        return ResponseEntity.status(HttpStatus.OK).body(this.role);
    }

}
