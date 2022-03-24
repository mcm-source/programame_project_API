package com.example.programame_project_api.entities;

import javax.persistence.*;

@Entity
@Table(name = "Usuarios")
public class AuthenticationRequest {
    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean block;
    @Enumerated
    @Column(nullable = false)
    private UserRole userRole;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
        this.block = false;
    }
    public AuthenticationRequest(String username, String password,Boolean block) {
        this.username = username;
        this.password = password;
        this.block = block;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
