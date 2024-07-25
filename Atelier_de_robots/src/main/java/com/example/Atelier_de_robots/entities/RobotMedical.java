package com.example.Atelier_de_robots.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("MEDICAL")
public class RobotMedical extends Robot {
    private String specialisationMedicale;

    // Parties spécifiques pour les robots médicaux
    private Set<String> partiesSpecialisees = new HashSet<>();

    public RobotMedical() {
        this.partiesSpecialisees.add("Scanner biométrique");
        this.partiesSpecialisees.add("Distributeur de médicament");
        this.partiesSpecialisees.add("Module de personnalité 'Infirmière'");
    }

    // Getters and Setters
    public String getSpecialisationMedicale() {
        return specialisationMedicale;
    }

    public void setSpecialisationMedicale(String specialisationMedicale) {
        this.specialisationMedicale = specialisationMedicale;
    }

    public Set<String> getPartiesSpecialisees() {
        return partiesSpecialisees;
    }

    public void setPartiesSpecialisees(Set<String> partiesSpecialisees) {
        this.partiesSpecialisees = partiesSpecialisees;
    }
}