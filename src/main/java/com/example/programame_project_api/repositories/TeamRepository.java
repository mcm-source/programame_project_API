package com.example.programame_project_api.repositories;

import com.example.programame_project_api.entities.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public interface TeamRepository  extends CrudRepository<Team, Long> {

    boolean existsByName(String name);
    boolean existsById(int id);
    Team findById(int id);
    Team findByName(String name);

    public default void update(int idTeam, Map<String, Object> teamData) {

        Team teamForUpdate= findById(idTeam);

        teamForUpdate.setName( (String) teamData.get("name"));
        teamForUpdate.setTeamMembers((String) teamData.get("teamMembers"));
        teamForUpdate.setSchoolName((String) teamData.get("schoolName"));
        teamForUpdate.setLocation( (String) teamData.get("location"));
        save(teamForUpdate);

    }



}
