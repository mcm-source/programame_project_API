package com.example.programame_project_api.repositories;

import com.example.programame_project_api.entities.ComplexDonation;
import org.springframework.data.repository.CrudRepository;

public interface    ComplexDonationRepository extends CrudRepository<ComplexDonation, Long> {

    @Override
    void deleteById(Long id);
}
