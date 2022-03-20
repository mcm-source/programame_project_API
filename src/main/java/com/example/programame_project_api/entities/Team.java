package com.example.programame_project_api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teams")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String teamMembers;
    @Column(nullable = false)
    private String schoolName;
    @Column(nullable = false)
    private String location;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Sponsor> listSponsors;


    public Team() {
    }

    public Team(String name, String teamMembers, String schoolName, String location, Teacher teacher) {
        this.name = name;
        this.teamMembers = teamMembers;
        this.schoolName = schoolName;
        this.location = location;
        this.teacher = teacher;
    }


}
