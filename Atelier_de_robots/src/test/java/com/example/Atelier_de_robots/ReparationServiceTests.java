package com.example.Atelier_de_robots;

import com.example.Atelier_de_robots.entities.*;
import com.example.Atelier_de_robots.services.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReparationServiceTests {

    @Autowired
    private RobotService robotService;

    @Autowired
    private FabricantService fabricantService;

    @Autowired
    private ReparationService reparationService;

    @BeforeEach
    public void setup() {
        robotService.deleteAllRobots();
    }

    @Test
    public void testCreateReparationWithInvalidDate() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant D");
        fabricant.setPays("Allemagne");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Réparation Date");
        robot.setModele("X400");
        robot.setDateFabrication(LocalDate.now().minusDays(5));
        robot.setStatut("incomplet");
        robot.setFabricant(fabricant);
        robot.setSpecialisationIndustrielle("Manutention");

        // Création des parties du robot industriel
        Set<PartieRobot> parties = new HashSet<>();
        parties.add(new PartieRobot(TypePartie.BRAS, robot));
        parties.add(new PartieRobot(TypePartie.JAMBE, robot));
        parties.add(new PartieRobot(TypePartie.TORSE, robot));
        parties.add(new PartieRobot(TypePartie.TETE, robot));
        parties.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robot));
        parties.add(new PartieRobot(TypePartie.BATTERIE, robot));
        parties.add(new PartieRobot(TypePartie.RENFORCEMENT, robot));
        parties.add(new PartieRobot(TypePartie.MODULE_SOUDE, robot));
        parties.add(new PartieRobot(TypePartie.EQUIPEMENTS_SERRAGE, robot));
        robot.setParties(parties);

        // Sauvegarde du robot
        robotService.createOrUpdateRobot(robot);

        // Création d'une réparation avec une date avant la date de fabrication
        Reparation reparation = new Reparation();
        reparation.setRobot(robot);
        reparation.setDescription("Réparation test");
        reparation.setCout(new BigDecimal("100.0"));

        // Test de l'exception lors de la définition de la date de réparation
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reparation.setDateReparation(LocalDate.now().minusDays(10)); // Date avant fabrication
        });

        String expectedMessage = "La date de réparation doit être postérieure à la date de fabrication du robot.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCannotHaveMoreThanThreeReparationsInOneYear() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant G");
        fabricant.setPays("USA");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Industriel Test");
        robot.setModele("X700");
        robot.setDateFabrication(LocalDate.now().minusDays(365));
        robot.setStatut("operationnel");
        robot.setFabricant(fabricant);
        robot.setSpecialisationIndustrielle("Assemblage");

        // Création des parties du robot industriel
        Set<PartieRobot> parties = new HashSet<>();
        parties.add(new PartieRobot(TypePartie.BRAS, robot));
        parties.add(new PartieRobot(TypePartie.JAMBE, robot));
        parties.add(new PartieRobot(TypePartie.TORSE, robot));
        parties.add(new PartieRobot(TypePartie.TETE, robot));
        parties.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robot));
        parties.add(new PartieRobot(TypePartie.BATTERIE, robot));
        parties.add(new PartieRobot(TypePartie.RENFORCEMENT, robot));
        parties.add(new PartieRobot(TypePartie.MODULE_SOUDE, robot));
        parties.add(new PartieRobot(TypePartie.EQUIPEMENTS_SERRAGE, robot));
        robot.setParties(parties);

        // Sauvegarde du robot
        robotService.createOrUpdateRobot(robot);

        // Création et sauvegarde des trois premières réparations
        for (int i = 1; i <= 3; i++) {
            Reparation reparation = new Reparation();
            reparation.setRobot(robot);
            reparation.setDateReparation(LocalDate.now().minusDays(30 * i)); // Réparations espacées d'un mois
            reparation.setDescription("Réparation test " + i);
            reparation.setCout(new BigDecimal("100.0"));

            reparationService.createOrUpdateReparation(reparation);
        }

        // Tentative de création d'une quatrième réparation dans la même année
        Reparation reparation4 = new Reparation();
        reparation4.setRobot(robot);
        reparation4.setDateReparation(LocalDate.now().minusDays(10)); // Réparation dans la même année
        reparation4.setDescription("Réparation test 4");
        reparation4.setCout(new BigDecimal("100.0"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reparationService.createOrUpdateReparation(reparation4);
        });

        String expectedMessage = "Un robot ne peut pas avoir plus de trois réparations dans une année calendaire.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testReparationCostMustBeGreaterThanZero() {
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant H");
        fabricant.setPays("Allemagne");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Industriel Test");
        robot.setModele("X800");
        robot.setDateFabrication(LocalDate.now().minusDays(200));
        robot.setStatut("operationnel");
        robot.setFabricant(fabricant);
        robot.setSpecialisationIndustrielle("Production");

        // Création des parties du robot industriel
        Set<PartieRobot> parties = new HashSet<>();
        parties.add(new PartieRobot(TypePartie.BRAS, robot));
        parties.add(new PartieRobot(TypePartie.JAMBE, robot));
        parties.add(new PartieRobot(TypePartie.TORSE, robot));
        parties.add(new PartieRobot(TypePartie.TETE, robot));
        parties.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robot));
        parties.add(new PartieRobot(TypePartie.BATTERIE, robot));
        parties.add(new PartieRobot(TypePartie.RENFORCEMENT, robot));
        parties.add(new PartieRobot(TypePartie.MODULE_SOUDE, robot));
        parties.add(new PartieRobot(TypePartie.EQUIPEMENTS_SERRAGE, robot));
        robot.setParties(parties);

        // Sauvegarde du robot
        robotService.createOrUpdateRobot(robot);

        // Tentative de création d'une réparation avec un coût de zéro
        Reparation reparation = new Reparation();
        reparation.setRobot(robot);
        reparation.setDateReparation(LocalDate.now());
        reparation.setDescription("Réparation coût zéro");
        reparation.setCout(BigDecimal.ZERO); // Coût zéro

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reparationService.createOrUpdateReparation(reparation);
        });

        String expectedMessage = "Le coût de la réparation doit être supérieur à zéro.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRobotStatusUpdatedToActiveAfterReparation() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant I");
        fabricant.setPays("France");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Industriel Test");
        robot.setModele("X900");
        robot.setDateFabrication(LocalDate.now().minusDays(200));
        robot.setStatut("maintenance");
        robot.setFabricant(fabricant);
        robot.setSpecialisationIndustrielle("Assemblage");

        // Création des parties du robot industriel
        Set<PartieRobot> parties = new HashSet<>();
        parties.add(new PartieRobot(TypePartie.BRAS, robot));
        parties.add(new PartieRobot(TypePartie.JAMBE, robot));
        parties.add(new PartieRobot(TypePartie.TORSE, robot));
        parties.add(new PartieRobot(TypePartie.TETE, robot));
        parties.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robot));
        parties.add(new PartieRobot(TypePartie.BATTERIE, robot));
        parties.add(new PartieRobot(TypePartie.RENFORCEMENT, robot));
        parties.add(new PartieRobot(TypePartie.MODULE_SOUDE, robot));
        parties.add(new PartieRobot(TypePartie.EQUIPEMENTS_SERRAGE, robot));
        robot.setParties(parties);

        // Sauvegarde du robot
        robotService.createOrUpdateRobot(robot);

        // Création de la réparation
        Reparation reparation = new Reparation();
        reparation.setRobot(robot);
        reparation.setDateReparation(LocalDate.now());
        reparation.setDescription("Réparation test");
        reparation.setCout(new BigDecimal("100.0"));

        // Sauvegarde de la réparation et fin de la réparation
        reparationService.createOrUpdateReparation(reparation);
        reparationService.completeReparation(reparation);

        // Vérifier que le statut du robot est mis à jour à "operationnel"
        assertEquals("operationnel", robot.getStatut());
    }
}
