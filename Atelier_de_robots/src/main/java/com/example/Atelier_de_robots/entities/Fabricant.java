package com.example.Atelier_de_robots.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Fabricant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String pays;

    @OneToMany(mappedBy = "fabricant")
    private Set<Robot> robots = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Set<Robot> getRobots() {
        return robots;
    }

    public void setRobots(Set<Robot> robots) {
        this.robots = robots;
    }

    // Getters and Setters
}
