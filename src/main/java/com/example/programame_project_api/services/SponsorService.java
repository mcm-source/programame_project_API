package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.*;
import com.example.programame_project_api.repositories.ComplexDonationRepository;
import com.example.programame_project_api.repositories.SimpleDonationRepository;
import com.example.programame_project_api.repositories.SponsorRepository;
import com.example.programame_project_api.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SponsorService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SponsorRepository sponsorRepository;
    @Autowired
    private SimpleDonationRepository simpleDonationRepository;
    @Autowired
    private ComplexDonationRepository complexDonationRepository;


    public ResponseEntity addSponsorToTeam(Map<String, Object> data) {

        try {

            Team team = teamRepository.findById((Integer) data.get("idTeam"));

            if (team != null) {

                List listSponsors = team.getListSponsors();
                listSponsors.add(createSponsorWithDonation(data));
                team.setListSponsors(listSponsors);
                teamRepository.save(team);


                return createResponseEntity(HttpStatus.OK, "Create sponsor ok");
            } else {
                return createResponseEntity(HttpStatus.NOT_ACCEPTABLE, "Team not exist");
            }


        } catch (
                Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    public ResponseEntity updateSponsor(Map<String, Object> data) {


        try {

            Team team = teamRepository.findById((Integer) data.get("idTeam"));

            if (team != null) {
                if (doUpdateSponsor(team, data)) {
                    return createResponseEntity(HttpStatus.OK, "Update sponsor ok");
                } else {
                    return createResponseEntity(HttpStatus.OK, "Update has fault");
                }

            } else {
                return createResponseEntity(HttpStatus.NOT_ACCEPTABLE, "Team not exist");
            }

        } catch (
                Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    public ResponseEntity deleteSponsor(int id) {

        try {
            Sponsor sponsor = sponsorRepository.findById(id);

            if (sponsor != null) {
                sponsorRepository.delete(sponsor);
                return createResponseEntity(HttpStatus.OK, "Sponsor delete Ok");
            } else {
                return createResponseEntity(HttpStatus.NOT_FOUND, "Sponsor not exist");
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }







    private boolean doUpdateSponsor(Team team, Map<String, Object> data) {


        int sponsorId = (Integer) data.get("idSponsor");
        List listSponsors = team.getListSponsors();
        AtomicReference<Boolean> udateOk = new AtomicReference<>(false);

        team.getListSponsors().stream()
                .filter((sponsorData) -> sponsorData.getId() == sponsorId)
                .findFirst()
                .ifPresentOrElse((sponsorConsumer) -> {
                    sponsorConsumer = clearDonationsFromSponsor(sponsorConsumer);
                    sponsorRepository.updateSponsor(sponsorConsumer, data);
                    udateOk.set(true);
                }, () -> {
                    udateOk.set(false);
                });
        /*
        // sponsorRepository.updateSponsor(sponsor, data);
        team.getListSponsors().forEach(sponsorData -> {

            if (sponsorData.getId() == sponsorId) {
                sponsorData = clearDonationsFromSponsor(sponsorData);
                sponsorRepository.updateSponsor(sponsorData, data);
                udateOk.set(true);
            }


        });
        */


        return udateOk.get();
    }


    private Sponsor clearDonationsFromSponsor(Sponsor sponsor) {

        SimpleDonation simpleDonation = sponsor.getSimpleDonation();
        ComplexDonation complexDonation = sponsor.getComplexDonation();


        if (simpleDonation != null) {
            simpleDonationRepository.delete(simpleDonation);
            sponsor.setSimpleDonation(null);
        }
        if (complexDonation != null) {
            complexDonationRepository.delete(complexDonation);
            sponsor.setComplexDonation(null);
        }
        return sponsor;

    }

    private Sponsor createSponsorWithDonation(Map<String, Object> data) {

        if ((Boolean) data.get("isSimpleDonation")) {
            return new Sponsor((String) data.get("name"),
                    new SimpleDonation((Float) data.get("amount"))
            );


        } else {
            return new Sponsor((String) data.get("name"),
                    new ComplexDonation(
                            (Float) data.get("amountForSimpleProblem"),
                            (Float) data.get("amountForMediumProblem"),
                            (Float) data.get("amountForHardProblem")

                    ));
        }


    }

    private ResponseEntity createResponseEntity(HttpStatus status, String bodyMessage) {

        return ResponseEntity
                .status(status)
                .body(bodyMessage);


    }
}




