package com.example.Atelier_de_robots.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Robot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String nom;

    private String modele;
    private LocalDate dateFabrication;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_fabricant")
    private Fabricant fabricant;

    @OneToMany(mappedBy = "robot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartieRobot> parties = new HashSet<>();

    @Column(nullable = false)
    private boolean reparationEnCours = false;

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
        if (dateFabrication.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de fabrication ne peut pas être une date future.");
        }
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

    public boolean isReparationEnCours() {
        return reparationEnCours;
    }
    
    public void setReparationEnCours(boolean reparationEnCours) {
        this.reparationEnCours = reparationEnCours;
    }


    protected boolean robotEstComplet() {
        Set<TypePartie> typesPartiesEssentielles = Set.of(
                TypePartie.BRAS,
                TypePartie.JAMBE,
                TypePartie.TORSE,
                TypePartie.TETE,
                TypePartie.PUCE_ELECTRONIQUE,
                TypePartie.BATTERIE
        );

        Set<TypePartie> typesPartiesRobot = getParties().stream()
                .map(PartieRobot::getType)
                .collect(Collectors.toSet());

        return typesPartiesEssentielles.stream().allMatch(type -> typesPartiesRobot.contains(type) &&
                getParties().stream().filter(partie -> partie.getType().equals(type)).count() == type.getMaxCount());
    }
}
