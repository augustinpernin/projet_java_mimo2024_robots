package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.Reparation;
import com.example.Atelier_de_robots.repositories.ReparationRepository;

@Service
public class ReparationService {

    @Autowired
    private ReparationRepository reparationRepository;

    // Méthodes CRUD pour Reparation
}
