package com.example.programame_project_api.repositories;

import com.example.programame_project_api.entities.ComplexDonation;
import com.example.programame_project_api.entities.SimpleDonation;
import com.example.programame_project_api.entities.Sponsor;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public interface SponsorRepository extends CrudRepository<Sponsor, Long> {

    Sponsor findById(int id);


    public default void updateSponsor(Sponsor sponsor, Map<String, Object> data) {

        if ((Boolean) data.get("isSimpleDonation")) {
            sponsor.setName((String) data.get("name"));
            sponsor.setSimpleDonation(new SimpleDonation((Float) data.get("amount")));
            save(sponsor);
        } else {
            sponsor.setName((String) data.get("name"));
            sponsor.setComplexDonation(new ComplexDonation(
                    (double) data.get("amountForSimpleProblem"),
                    (double) data.get("amountForMediumProblem"),
                    (double) data.get("amountForHardProblem")
            ));
            save(sponsor);
        }
    }
}
