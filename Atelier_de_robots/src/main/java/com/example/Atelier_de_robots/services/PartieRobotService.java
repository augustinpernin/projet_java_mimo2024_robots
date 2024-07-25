package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.PartieRobot;
import com.example.Atelier_de_robots.repositories.PartieRobotRepository;

@Service
public class PartieRobotService {

    @Autowired
    private PartieRobotRepository partieRobotRepository;

    // Méthodes CRUD pour PartieRobot
}
