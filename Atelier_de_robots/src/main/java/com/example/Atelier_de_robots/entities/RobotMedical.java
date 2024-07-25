package com.example.Atelier_de_robots.entities;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

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
