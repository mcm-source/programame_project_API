package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.*;
import com.example.programame_project_api.repositories.*;
import com.example.programame_project_api.security.JWTUtil;
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
    private TeacherRepository teacherRepository;
    @Autowired
    private SponsorRepository sponsorRepository;
    @Autowired
    private SimpleDonationRepository simpleDonationRepository;
    @Autowired
    private ComplexDonationRepository complexDonationRepository;
    @Autowired
    private JWTUtil jwtUtil;


    public ResponseEntity addSponsorToTeam(Map<String, Object> data, String token) {

        try {

            Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));
            int idTeam = (int) data.get("idTeam");

            if (teacher.haveTheTeam(idTeam)) {
                Team team = teamRepository.findById((Integer) data.get("idTeam"));
               sponsorRepository.save( createSponsorWithDonation(data, team));
                teamRepository.save(team);
                return createResponseEntity(HttpStatus.OK, "Create sponsor ok");
            } else {
                return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, "Teacher doesn´t have the team");
            }


        } catch (Exception e) {
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }


    public ResponseEntity updateSponsor(Map<String, Object> data, String token) {


        try {

            Teacher teacher = teacherRepository.findByEmail(extractEmailFromToken(token));
            int idTeam = (int) data.get("idTeam");



            if (teacher.haveTheTeam(idTeam)) {
                Team team = teamRepository.findById((Integer) data.get("idTeam"));
                if (doUpdateSponsor(team, data)) {
                    return createResponseEntity(HttpStatus.OK, "Update sponsor ok");
                } else {
                    return createResponseEntity(HttpStatus.OK, "Update has fault");
                }

            } else {
                return createResponseEntity(HttpStatus.NOT_ACCEPTABLE, "Team not exist");
            }

        } catch (Exception e) {
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
                    complexDonationRepository.delete(sponsorConsumer.getComplexDonation());
                    sponsorConsumer = clearDonationsFromSponsor(sponsorConsumer);
                    clearDonationsFromSponsor(sponsorConsumer);
                    sponsorRepository.updateSponsor(sponsorConsumer, data);
                    udateOk.set(true);
                }, () -> {
                    udateOk.set(false);
                });

        return udateOk.get();
    }


    private Sponsor clearDonationsFromSponsor(Sponsor sponsor) {

        SimpleDonation simpleDonation = sponsor.getSimpleDonation();
        ComplexDonation complexDonation = sponsor.getComplexDonation();


        if (simpleDonation != null) {
            System.out.println("id donacion"+simpleDonation.getId());
            simpleDonation.setSponsor(null);
            sponsor.setSimpleDonation(null);
            sponsorRepository.save(sponsor);
            simpleDonationRepository.deleteById(simpleDonation.getId());


        }
        if (complexDonation != null) {
            System.out.println("id donacion"+complexDonation.getId());
            complexDonation.setSponsor(null);
            sponsor.setComplexDonation(null);
            sponsorRepository.save(sponsor);
            complexDonationRepository.deleteById(complexDonation.getId());


        }
        return sponsor;

    }

    private Sponsor createSponsorWithDonation(Map<String, Object> data, Team team) {

        if ((Boolean) data.get("isSimpleDonation")) {
            return new Sponsor((String) data.get("name"),
                    team,
                    new SimpleDonation((double) data.get("amount"))
            );


        } else {
            return new Sponsor((String) data.get("name"),
                    team,
                    new ComplexDonation(
                            (double) data.get("amountForSimpleProblem"),
                            (double) data.get("amountForMediumProblem"),
                            (double) data.get("amountForHardProblem")

                    ));
        }


    }

    private ResponseEntity createResponseEntity(HttpStatus status, String bodyMessage) {

        return ResponseEntity
                .status(status)
                .body(bodyMessage);


    }


    private String extractEmailFromToken(String token){

        System.out.println(token);
        String dato =token;
        dato = dato.replace("Bearer ","");
        System.out.println(dato);
        return  jwtUtil.extractUsername(dato);


    }
}




