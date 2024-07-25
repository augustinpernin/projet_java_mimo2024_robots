package com.example.Atelier_de_robots.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("INDUSTRIEL")
public class RobotIndustriel extends Robot {
    private String specialisationIndustrielle;

    // Parties spécifiques pour les robots industriels
    private Set<String> partiesSpecialisees = new HashSet<>();

    public RobotIndustriel() {
        this.partiesSpecialisees.add("Renforcement pour lever de charges lourdes");
        this.partiesSpecialisees.add("Module de soudure");
        this.partiesSpecialisees.add("Equipements de serrage");
    }

    // Getters and Setters
    public String getSpecialisationIndustrielle() {
        return specialisationIndustrielle;
    }

    public void setSpecialisationIndustrielle(String specialisationIndustrielle) {
        this.specialisationIndustrielle = specialisationIndustrielle;
    }

    public Set<String> getPartiesSpecialisees() {
        return partiesSpecialisees;
    }

    public void setPartiesSpecialisees(Set<String> partiesSpecialisees) {
        this.partiesSpecialisees = partiesSpecialisees;
    }
}