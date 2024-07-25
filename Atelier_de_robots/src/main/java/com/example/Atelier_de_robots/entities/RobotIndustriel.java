package com.example.Atelier_de_robots.entities;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("INDUSTRIEL")
public class RobotIndustriel extends Robot {
    private String specialisationIndustrielle;

    public String getSpecialisationIndustrielle() {
        return specialisationIndustrielle;
    }

    public void setSpecialisationIndustrielle(String specialisationIndustrielle) {
        this.specialisationIndustrielle = specialisationIndustrielle;
    }

    
}
