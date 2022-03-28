package com.example.programame_project_api.repositories;

import com.example.programame_project_api.entities.persistEntities.SimpleDonation;
import org.springframework.data.repository.CrudRepository;

public interface SimpleDonationRepository extends CrudRepository<SimpleDonation, Long> {

    @Override
    void deleteById(Long id);
}
