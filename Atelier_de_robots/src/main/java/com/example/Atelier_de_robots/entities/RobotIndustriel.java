package com.example.Atelier_de_robots.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

        @Override
    protected boolean robotEstComplet() {
        Set<TypePartie> typesPartiesEssentielles = Set.of(
                TypePartie.BRAS,
                TypePartie.JAMBE,
                TypePartie.TORSE,
                TypePartie.TETE,
                TypePartie.PUCE_ELECTRONIQUE,
                TypePartie.BATTERIE,
                TypePartie.RENFORCEMENT,
                TypePartie.MODULE_SOUDE,
                TypePartie.EQUIPEMENTS_SERRAGE
        );

        Set<TypePartie> typesPartiesRobot = getParties().stream()
                .map(PartieRobot::getType)
                .collect(Collectors.toSet());

        return typesPartiesEssentielles.stream().allMatch(type -> typesPartiesRobot.contains(type) &&
                getParties().stream().filter(partie -> partie.getType().equals(type)).count() == type.getMaxCount());
    }
}