package com.example.Atelier_de_robots.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("MEDICAL")
public class RobotMedical extends Robot {
    private String specialisationMedicale;

    public String getSpecialisationMedicale() {
        return specialisationMedicale;
    }

    public void setSpecialisationMedicale(String specialisationMedicale) {
        this.specialisationMedicale = specialisationMedicale;
    }

}
