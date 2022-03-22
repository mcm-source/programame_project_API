package com.example.programame_project_api.repositories;


import com.example.programame_project_api.entities.AuthenticationRequest;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AuthenticationRequest, Long> {
    AuthenticationRequest findByUsername(String username);
    boolean existsByUsername(String username);


    public default void update(AuthenticationRequest user){

        AuthenticationRequest userData = findByUsername(user.getUsername());
        userData.setUsername(userData.getUsername());
        userData.setPassword(user.getPassword());


        save(userData);

    }
}
