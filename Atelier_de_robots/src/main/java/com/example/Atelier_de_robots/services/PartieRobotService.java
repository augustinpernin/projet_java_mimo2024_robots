package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.PartieRobot;
import com.example.Atelier_de_robots.repositories.PartieRobotRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PartieRobotService {

    @Autowired
    private PartieRobotRepository partieRobotRepository;

    // Create or Update
    public PartieRobot createOrUpdatePartieRobot(PartieRobot partieRobot) {
        return partieRobotRepository.save(partieRobot);
    }

    // Read all
    public List<PartieRobot> findAllPartiesRobot() {
        return partieRobotRepository.findAll();
    }

    // Read by ID
    public PartieRobot findPartieRobotById(Long id) {
        Optional<PartieRobot> partieRobot = partieRobotRepository.findById(id);
        return partieRobot.orElse(null);
    }

    // Delete
    public void deletePartieRobot(PartieRobot partieRobot) {
        partieRobotRepository.delete(partieRobot);
    }
}
