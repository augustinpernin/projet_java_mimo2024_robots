package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.Fabricant;
import com.example.Atelier_de_robots.repositories.FabricantRepository;

@Service
public class FabricantService {

    @Autowired
    private FabricantRepository fabricantRepository;

    // Méthodes CRUD pour Fabricant
}
