package com.example.programame_project_api.entities;

import javax.persistence.*;

@Entity
@Table(name = "Usuarios")
public class AuthenticationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated
    @Column(nullable = false)
    private UserRole userRole;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
