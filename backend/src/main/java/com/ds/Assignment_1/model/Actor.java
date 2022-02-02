package com.ds.Assignment_1.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
public class Actor {
    @Id
    private String username;
    private String password;
    private String role;

    @OneToOne(mappedBy = "user")
    private Client client;


    public Actor(){}

    public Actor(String email, String password, String role) {
        this.username = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
