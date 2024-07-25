package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.Robot;
import com.example.Atelier_de_robots.repositories.RobotRepository;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class RobotService {

    @Autowired
    private RobotRepository robotRepository;

    public Robot createOrUpdateRobot(Robot robot) {
        if (robotEstComplet(robot)) {
            robot.setStatut("operationnel");
        } else {
            robot.setStatut("incomplet");
        }
        return robotRepository.save(robot);
    }

    private boolean robotEstComplet(Robot robot) {
        Set<String> typesPartiesNecessaires = new HashSet<>(Arrays.asList("Bras", "Jambe", "Torse", "Tête", "Puce_électronique", "Batterie"));
        Set<String> typesPartiesRobot = robot.getParties().stream()
                                             .map(PartieRobot::getType)
                                             .collect(Collectors.toSet());
        return typesPartiesNecessaires.equals(typesPartiesRobot);
    }


}
