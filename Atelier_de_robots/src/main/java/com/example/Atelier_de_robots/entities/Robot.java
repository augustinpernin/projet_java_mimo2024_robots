package com.example.Atelier_de_robots.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Robot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String modele;
    private LocalDate dateFabrication;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_fabricant")
    private Fabricant fabricant;

    @OneToMany(mappedBy = "robot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartieRobot> parties = new HashSet<>();

    // Getters and Setters

    public int getNombreRobotsProduitsModele(String modele) {
        // Logic to get the number of robots produced for a specific model
        return 0; // Placeholder
    }

    public int getNombreRobotsProduits() {
        // Logic to get the total number of robots produced
        return 0; // Placeholder
    }

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

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public LocalDate getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(LocalDate dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Fabricant getFabricant() {
        return fabricant;
    }

    public void setFabricant(Fabricant fabricant) {
        this.fabricant = fabricant;
    }

    public Set<PartieRobot> getParties() {
        return parties;
    }
    public void setParties(Set<PartieRobot> parties) {
        this.parties = parties;
    }
}
