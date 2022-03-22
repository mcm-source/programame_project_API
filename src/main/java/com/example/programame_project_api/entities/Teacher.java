package com.example.programame_project_api.entities;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name = "Teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Column(unique = true, nullable = false)
    private  String email;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Team> listTeams;

    public Teacher() {
    }

    public Teacher(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public long getId() {
        return id;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getListTeams() {
        return listTeams;
    }

    public void setListTeams(List<Team> listTeams) {
        this.listTeams = listTeams;
    }

    public   boolean haveTheTeam( long idTeam) {


        AtomicReference<Boolean> hasTeam = new AtomicReference<>(false);
        listTeams.stream()
                .filter((teamData) -> teamData.getId() == idTeam)
                .findFirst()
                .ifPresentOrElse((teamConsumer) -> {
                    hasTeam.set(true);
                }, () -> {
                    hasTeam.set(false);

                });

        return hasTeam.get();
    }


}
