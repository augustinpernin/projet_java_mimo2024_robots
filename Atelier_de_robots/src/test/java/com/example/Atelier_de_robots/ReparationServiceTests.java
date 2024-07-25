package com.example.Atelier_de_robots;

import com.example.Atelier_de_robots.entities.*;
import com.example.Atelier_de_robots.services.FabricantService;
import com.example.Atelier_de_robots.services.RobotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReparationServiceTests {

    @Autowired
    private RobotService robotService;

    @Autowired
    private FabricantService fabricantService;

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
}
