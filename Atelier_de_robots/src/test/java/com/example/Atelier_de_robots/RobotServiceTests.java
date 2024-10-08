package com.example.Atelier_de_robots;

import com.example.Atelier_de_robots.entities.*;
import com.example.Atelier_de_robots.services.FabricantService;
import com.example.Atelier_de_robots.services.RobotService;
import com.example.Atelier_de_robots.services.ReparationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RobotServiceTests {

    @Autowired
    private RobotService robotService;

    @Autowired
    private FabricantService fabricantService;

    @Autowired
    private ReparationService reparationService;

    @Test
    public void testCreateOrUpdateRobotWithUniqueName() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant A");
        fabricant.setPays("France");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot industriel avec un nom unique
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Industriel Unique");
        robot.setModele("X200");
        robot.setDateFabrication(LocalDate.now());
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

        // Sauvegarde du robot (devrait être opérationnel)
        Robot savedRobot = robotService.createOrUpdateRobot(robot);
        assertEquals("operationnel", savedRobot.getStatut());
    }

    @Test
    public void testCreateRobotWithDuplicateName() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant B");
        fabricant.setPays("USA");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du premier robot médical avec un nom
        RobotMedical robot1 = new RobotMedical();
        robot1.setNom("Robot Médical Duplicate");
        robot1.setModele("M300");
        robot1.setDateFabrication(LocalDate.now());
        robot1.setStatut("incomplet");
        robot1.setFabricant(fabricant);
        robot1.setSpecialisationMedicale("Chirurgie");

        // Création des parties du premier robot médical
        Set<PartieRobot> parties1 = new HashSet<>();
        parties1.add(new PartieRobot(TypePartie.BRAS, robot1));
        parties1.add(new PartieRobot(TypePartie.JAMBE, robot1));
        parties1.add(new PartieRobot(TypePartie.TORSE, robot1));
        parties1.add(new PartieRobot(TypePartie.TETE, robot1));
        parties1.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robot1));
        parties1.add(new PartieRobot(TypePartie.BATTERIE, robot1));
        parties1.add(new PartieRobot(TypePartie.SCANNER_BIOMETRIQUE, robot1));
        parties1.add(new PartieRobot(TypePartie.DISTRIBUTEUR_MEDICAMENT, robot1));
        parties1.add(new PartieRobot(TypePartie.MODULE_PERSONNALITE, robot1));
        robot1.setParties(parties1);

        // Sauvegarde du premier robot (devrait être opérationnel)
        robotService.createOrUpdateRobot(robot1);

        // Création du deuxième robot médical avec le même nom
        RobotMedical robot2 = new RobotMedical();
        robot2.setNom("Robot Médical Duplicate");
        robot2.setModele("M400");
        robot2.setDateFabrication(LocalDate.now());
        robot2.setStatut("incomplet");
        robot2.setFabricant(fabricant);
        robot2.setSpecialisationMedicale("Soins Intensifs");

        // Création des parties du deuxième robot médical
        Set<PartieRobot> parties2 = new HashSet<>();
        parties2.add(new PartieRobot(TypePartie.BRAS, robot2));
        parties2.add(new PartieRobot(TypePartie.JAMBE, robot2));
        parties2.add(new PartieRobot(TypePartie.TORSE, robot2));
        parties2.add(new PartieRobot(TypePartie.TETE, robot2));
        parties2.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robot2));
        parties2.add(new PartieRobot(TypePartie.BATTERIE, robot2));
        parties2.add(new PartieRobot(TypePartie.SCANNER_BIOMETRIQUE, robot2));
        parties2.add(new PartieRobot(TypePartie.DISTRIBUTEUR_MEDICAMENT, robot2));
        parties2.add(new PartieRobot(TypePartie.MODULE_PERSONNALITE, robot2));
        robot2.setParties(parties2);

        // Sauvegarde du deuxième robot (devrait lever une exception)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.createOrUpdateRobot(robot2);
        });

        String expectedMessage = "Le nom du robot doit être unique.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateRobotWithFutureFabricationDate() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant C");
        fabricant.setPays("Canada");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot avec une date de fabrication future
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Future Date");
        robot.setModele("X300");
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

        // Test de l'exception lors de la définition de la date de fabrication
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            robot.setDateFabrication(LocalDate.now().plusDays(1)); // Date future
        });

        String expectedMessage = "La date de fabrication ne peut pas être une date future.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCannotSetRobotToMaintenanceOrOutOfServiceIfReparationInProgress() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant E");
        fabricant.setPays("Italie");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Test Maintenance");
        robot.setModele("X500");
        robot.setDateFabrication(LocalDate.now().minusDays(5));
        robot.setStatut("operationnel");
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

        // Début d'une réparation
        Reparation reparation = new Reparation();
        reparation.setRobot(robot);
        reparation.setDateReparation(LocalDate.now());
        reparation.setDescription("Réparation test");
        reparation.setCout(new BigDecimal("200.0"));

        // Sauvegarde de la réparation
        reparationService.createOrUpdateReparation(reparation);

        // Tentative de mise à jour du statut du robot en maintenance
        robot.setStatut("maintenance");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            robotService.createOrUpdateRobot(robot);
        });

        String expectedMessage = "Un robot ne peut pas être mis en maintenance ou hors service si une réparation est en cours.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testBaseRobotIsOperationalOnlyIfComplete() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant J");
        fabricant.setPays("France");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot incomplet
        Robot robot = new Robot();
        robot.setNom("Robot Incomplet Test");
        robot.setModele("X1000");
        robot.setDateFabrication(LocalDate.now().minusDays(200));
        robot.setStatut("incomplet");
        robot.setFabricant(fabricant);

        // Création des parties manquantes
        Set<PartieRobot> partiesIncompletes = new HashSet<>();
        partiesIncompletes.add(new PartieRobot(TypePartie.BRAS, robot));
        partiesIncompletes.add(new PartieRobot(TypePartie.TORSE, robot));
        partiesIncompletes.add(new PartieRobot(TypePartie.TETE, robot));
        partiesIncompletes.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robot));
        partiesIncompletes.add(new PartieRobot(TypePartie.BATTERIE, robot));
        robot.setParties(partiesIncompletes);

        // Sauvegarde du robot incomplet
        robot = robotService.createOrUpdateRobot(robot);
        assertEquals("incomplet", robot.getStatut());

        // Ajout des parties manquantes pour le rendre complet
        partiesIncompletes.add(new PartieRobot(TypePartie.JAMBE, robot));
        robot.setParties(partiesIncompletes);

        // Sauvegarde du robot complet
        robot = robotService.createOrUpdateRobot(robot);
        assertEquals("operationnel", robot.getStatut());
    }

    @Test
    public void testIndustrielRobotIsOperationalOnlyIfComplete() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant K");
        fabricant.setPays("USA");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot industriel incomplet
        RobotIndustriel robotIndustriel = new RobotIndustriel();
        robotIndustriel.setNom("Robot Industriel Incomplet Test");
        robotIndustriel.setModele("X1100");
        robotIndustriel.setDateFabrication(LocalDate.now().minusDays(200));
        robotIndustriel.setStatut("incomplet");
        robotIndustriel.setFabricant(fabricant);
        robotIndustriel.setSpecialisationIndustrielle("Assemblage");

        // Création des parties manquantes
        Set<PartieRobot> partiesIncompletes = new HashSet<>();
        partiesIncompletes.add(new PartieRobot(TypePartie.BRAS, robotIndustriel));
        partiesIncompletes.add(new PartieRobot(TypePartie.TORSE, robotIndustriel));
        partiesIncompletes.add(new PartieRobot(TypePartie.TETE, robotIndustriel));
        partiesIncompletes.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robotIndustriel));
        partiesIncompletes.add(new PartieRobot(TypePartie.BATTERIE, robotIndustriel));
        robotIndustriel.setParties(partiesIncompletes);

        // Sauvegarde du robot industriel incomplet
        robotIndustriel = (RobotIndustriel) robotService.createOrUpdateRobot(robotIndustriel);
        assertEquals("incomplet", robotIndustriel.getStatut());

        // Ajout des parties manquantes pour le rendre complet
        partiesIncompletes.add(new PartieRobot(TypePartie.JAMBE, robotIndustriel));
        partiesIncompletes.add(new PartieRobot(TypePartie.RENFORCEMENT, robotIndustriel));
        partiesIncompletes.add(new PartieRobot(TypePartie.MODULE_SOUDE, robotIndustriel));
        partiesIncompletes.add(new PartieRobot(TypePartie.EQUIPEMENTS_SERRAGE, robotIndustriel));
        robotIndustriel.setParties(partiesIncompletes);

        // Sauvegarde du robot industriel complet
        robotIndustriel = (RobotIndustriel) robotService.createOrUpdateRobot(robotIndustriel);
        assertEquals("operationnel", robotIndustriel.getStatut());
    }

    @Test
    public void testMedicalRobotIsOperationalOnlyIfComplete() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant L");
        fabricant.setPays("Japon");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot médical incomplet
        RobotMedical robotMedical = new RobotMedical();
        robotMedical.setNom("Robot Médical Incomplet Test");
        robotMedical.setModele("X1200");
        robotMedical.setDateFabrication(LocalDate.now().minusDays(200));
        robotMedical.setStatut("incomplet");
        robotMedical.setFabricant(fabricant);
        robotMedical.setSpecialisationMedicale("Chirurgie");

        // Création des parties manquantes
        Set<PartieRobot> partiesIncompletes = new HashSet<>();
        partiesIncompletes.add(new PartieRobot(TypePartie.BRAS, robotMedical));
        partiesIncompletes.add(new PartieRobot(TypePartie.TORSE, robotMedical));
        partiesIncompletes.add(new PartieRobot(TypePartie.TETE, robotMedical));
        partiesIncompletes.add(new PartieRobot(TypePartie.PUCE_ELECTRONIQUE, robotMedical));
        partiesIncompletes.add(new PartieRobot(TypePartie.BATTERIE, robotMedical));
        robotMedical.setParties(partiesIncompletes);

        // Sauvegarde du robot médical incomplet
        robotMedical = (RobotMedical) robotService.createOrUpdateRobot(robotMedical);
        assertEquals("incomplet", robotMedical.getStatut());

        // Ajout des parties manquantes pour le rendre complet
        partiesIncompletes.add(new PartieRobot(TypePartie.JAMBE, robotMedical));
        partiesIncompletes.add(new PartieRobot(TypePartie.SCANNER_BIOMETRIQUE, robotMedical));
        partiesIncompletes.add(new PartieRobot(TypePartie.DISTRIBUTEUR_MEDICAMENT, robotMedical));
        partiesIncompletes.add(new PartieRobot(TypePartie.MODULE_PERSONNALITE, robotMedical));
        robotMedical.setParties(partiesIncompletes);

        // Sauvegarde du robot médical complet
        robotMedical = (RobotMedical) robotService.createOrUpdateRobot(robotMedical);
        assertEquals("operationnel", robotMedical.getStatut());
    }
}
