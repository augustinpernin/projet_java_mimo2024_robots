package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.*;
import com.example.Atelier_de_robots.repositories.RobotRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RobotService {

    @Autowired
    private RobotRepository robotRepository;

    // Create or Update
    public Robot createOrUpdateRobot(Robot robot) {
        if (!isNomUnique(robot.getNom(), robot.getId())) {
            throw new IllegalArgumentException("Le nom du robot doit être unique.");
        }

        if (robotEstComplet(robot)) {
            robot.setStatut("operationnel");
        } else {
            robot.setStatut("incomplet");
        }
        return robotRepository.save(robot);
    }

    // Read all
    public List<Robot> findAllRobots() {
        return robotRepository.findAll();
    }

    // Read by ID
    public Robot findRobotById(Long id) {
        Optional<Robot> robot = robotRepository.findById(id);
        return robot.orElse(null);
    }

    // Delete
    public void deleteRobot(Robot robot) {
        robotRepository.delete(robot);
    }

    private boolean robotEstComplet(Robot robot) {
        Map<TypePartie, Long> partsCount = robot.getParties().stream()
            .collect(Collectors.groupingBy(PartieRobot::getType, Collectors.counting()));

        // Vérification des parties standards
        for (TypePartie type : EnumSet.of(TypePartie.BRAS, TypePartie.JAMBE, TypePartie.TORSE, TypePartie.BATTERIE, TypePartie.PUCE_ELECTRONIQUE)) {
            long count = partsCount.getOrDefault(type, 0L);
            if (count > type.getMaxCount() || (count == 0 && type.getMaxCount() > 0)) {
                return false;
            }
        }

        // Vérification des parties spécialisées
        if (robot instanceof RobotIndustriel) {
            for (TypePartie type : EnumSet.of(TypePartie.RENFORCEMENT, TypePartie.MODULE_SOUDE, TypePartie.EQUIPEMENTS_SERRAGE)) {
                long count = partsCount.getOrDefault(type, 0L);
                if (count > type.getMaxCount() || count == 0) {
                    return false;
                }
            }
        } else if (robot instanceof RobotMedical) {
            for (TypePartie type : EnumSet.of(TypePartie.SCANNER_BIOMETRIQUE, TypePartie.DISTRIBUTEUR_MEDICAMENT, TypePartie.MODULE_PERSONNALITE)) {
                long count = partsCount.getOrDefault(type, 0L);
                if (count > type.getMaxCount() || count == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isNomUnique(String nom, Long id) {
        List<Robot> robots = robotRepository.findAll();
        for (Robot existingRobot : robots) {
            if (existingRobot.getNom().equals(nom) && !Objects.equals(existingRobot.getId(), id)) {
                return false;
            }
        }
        return true;
    }   

        private void validateRobotDates(Robot robot) {
        if (robot.getDateFabrication().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de fabrication ne peut pas être une date future.");
        }
    }
}
