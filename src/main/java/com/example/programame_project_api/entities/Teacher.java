package com.example.programame_project_api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Column(nullable = false)
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
}
