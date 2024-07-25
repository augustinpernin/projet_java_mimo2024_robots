package com.example.Atelier_de_robots.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    protected boolean robotEstComplet() {
        Set<TypePartie> typesPartiesEssentielles = Set.of(
                TypePartie.BRAS,
                TypePartie.JAMBE,
                TypePartie.TORSE,
                TypePartie.TETE,
                TypePartie.PUCE_ELECTRONIQUE,
                TypePartie.BATTERIE,
                TypePartie.SCANNER_BIOMETRIQUE,
                TypePartie.DISTRIBUTEUR_MEDICAMENT,
                TypePartie.MODULE_PERSONNALITE
        );

        Set<TypePartie> typesPartiesRobot = getParties().stream()
                .map(PartieRobot::getType)
                .collect(Collectors.toSet());

        return typesPartiesEssentielles.stream().allMatch(type -> typesPartiesRobot.contains(type) &&
                getParties().stream().filter(partie -> partie.getType().equals(type)).count() == type.getMaxCount());
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