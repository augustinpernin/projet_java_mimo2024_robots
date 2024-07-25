package com.example.Atelier_de_robots;

import com.example.Atelier_de_robots.entities.Fabricant;
import com.example.Atelier_de_robots.entities.PartieRobot;
import com.example.Atelier_de_robots.entities.Robot;
import com.example.Atelier_de_robots.services.RobotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RobotServiceTests {

    @Autowired
    private RobotService robotService;

    @Test
    public void testCreateOrUpdateRobot() {
        // Création du fabricant
        Fabricant fabricant = new Fabricant();
        fabricant.setNom("Fabricant A");
        fabricant.setPays("France");

        // Création du robot
        Robot robot = new Robot();
        robot.setNom("Robot1");
        robot.setModele("X200");
        robot.setDateFabrication(LocalDate.now());
        robot.setStatut("incomplet");
        robot.setFabricant(fabricant);

        // Création des parties du robot (incomplet)
        PartieRobot bras = new PartieRobot();
        bras.setType("Bras");
        bras.setRobot(robot);

        PartieRobot jambe = new PartieRobot();
        jambe.setType("Jambe");
        jambe.setRobot(robot);

        // Ajout des parties au robot
        Set<PartieRobot> parties = new HashSet<>();
        parties.add(bras);
        parties.add(jambe);
        robot.setParties(parties);

        // Sauvegarde du robot (devrait être incomplet)
        robotService.createOrUpdateRobot(robot);
        assertEquals("incomplet", robot.getStatut());

        // Ajout des parties manquantes pour rendre le robot complet
        PartieRobot torse = new PartieRobot();
        torse.setType("Torse");
        torse.setRobot(robot);

        PartieRobot tete = new PartieRobot();
        tete.setType("Tête");
        tete.setRobot(robot);

        PartieRobot puce = new PartieRobot();
        puce.setType("Puce_électronique");
        puce.setRobot(robot);

        PartieRobot batterie = new PartieRobot();
        batterie.setType("Batterie");
        batterie.setRobot(robot);

        // Ajout des nouvelles parties au robot
        robot.getParties().add(torse);
        robot.getParties().add(tete);
        robot.getParties().add(puce);
        robot.getParties().add(batterie);

        // Mise à jour du robot (devrait être opérationnel)
        robotService.createOrUpdateRobot(robot);
        assertEquals("operationnel", robot.getStatut());
    }
}
