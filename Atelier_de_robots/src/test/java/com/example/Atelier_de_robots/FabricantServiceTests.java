package com.example.Atelier_de_robots;

import com.example.Atelier_de_robots.entities.Fabricant;
import com.example.Atelier_de_robots.entities.RobotIndustriel;
import com.example.Atelier_de_robots.entities.PartieRobot;
import com.example.Atelier_de_robots.entities.TypePartie;
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
public class FabricantServiceTests {

    @Autowired
    private FabricantService fabricantService;

    @Autowired
    private RobotService robotService;

    @Test
    public void testCannotDeleteFabricantWithAssociatedRobots() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant F");
        fabricant.setPays("Japon");

        // Sauvegarde du fabricant
        fabricantService.createOrUpdateFabricant(fabricant);

        // Création du robot associé
        RobotIndustriel robot = new RobotIndustriel();
        robot.setNom("Robot Industriel Test");
        robot.setModele("X600");
        robot.setDateFabrication(LocalDate.now().minusDays(5));
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

        // Tentative de suppression du fabricant
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fabricantService.deleteFabricant(fabricant);
        });

        String expectedMessage = "Un fabricant ne peut pas être supprimé s'il y a encore des robots associés à ce fabricant.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
