package com.example.Atelier_de_robots;

import com.example.Atelier_de_robots.entities.*;
import com.example.Atelier_de_robots.services.FabricantService;
import com.example.Atelier_de_robots.services.RobotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
