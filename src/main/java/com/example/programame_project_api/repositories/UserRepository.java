package com.example.programame_project_api.repositories;


import com.example.programame_project_api.entities.persistEntities.AuthenticationRequest;
import com.example.programame_project_api.entities.persistEntities.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<AuthenticationRequest, Long> {
    AuthenticationRequest findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsById(long id);
    List<AuthenticationRequest> findAll();

    public default void update(AuthenticationRequest user, String oldUsername){

        AuthenticationRequest userData = findByUsername(oldUsername);
        userData.setUsername(userData.getUsername());
        userData.setPassword(user.getPassword());
        save(userData);

    }
}
