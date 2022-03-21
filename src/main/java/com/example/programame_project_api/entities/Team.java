package com.example.programame_project_api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teams")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(unique = true, nullable = false)
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

    public Team(String name,
                String teamMembers,
                String schoolName,
                String location,
                Teacher teacher)
                {
        this.name = name;
        this.teamMembers = teamMembers;
        this.schoolName = schoolName;
        this.location = location;
        this.teacher = teacher;

    }


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Sponsor> getListSponsors() {
        return listSponsors;
    }

    public void setListSponsors(List<Sponsor> listSponsors) {
        this.listSponsors = listSponsors;
    }
}
