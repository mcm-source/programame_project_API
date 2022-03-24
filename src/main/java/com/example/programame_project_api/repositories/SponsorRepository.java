package com.example.programame_project_api.repositories;

import com.example.programame_project_api.entities.ComplexDonation;
import com.example.programame_project_api.entities.SimpleDonation;
import com.example.programame_project_api.entities.Sponsor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface SponsorRepository extends CrudRepository<Sponsor, Long> {

    Sponsor findById(long id);

    List<Sponsor> findAll();


    public default void updateSponsor(Sponsor sponsor, Map<String, Object> data) {

        if ((Boolean) data.get("isSimpleDonation")) {
            sponsor.setName((String) data.get("name"));
//            sponsor.setSimpleDonation(new SimpleDonation((double) data.get("amount")));
            sponsor.setSimpleDonation(new SimpleDonation(Double.parseDouble((String )data.get("amount"))));
            save(sponsor);
        } else {
            sponsor.setName((String) data.get("name"));
            sponsor.setComplexDonation(new ComplexDonation(
//                    (double) data.get("amountForSimpleProblem"),
//                    (double) data.get("amountForMediumProblem"),
//                    (double) data.get("amountForHardProblem")
                    Double.parseDouble((String )data.get("amountForSimpleProblem")),
                    Double.parseDouble((String )data.get("amountForMediumProblem")),
                    Double.parseDouble((String )data.get("amountForHardProblem"))
            ));
            save(sponsor);
        }
    }
}
