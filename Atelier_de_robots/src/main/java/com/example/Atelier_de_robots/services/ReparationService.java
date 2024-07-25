package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.Reparation;
import com.example.Atelier_de_robots.repositories.ReparationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReparationService {

    @Autowired
    private ReparationRepository reparationRepository;

    // Create or Update
    public Reparation createOrUpdateReparation(Reparation reparation) {
        return reparationRepository.save(reparation);
    }

    // Read all
    public List<Reparation> findAllReparations() {
        return reparationRepository.findAll();
    }

    // Read by ID
    public Reparation findReparationById(Long id) {
        Optional<Reparation> reparation = reparationRepository.findById(id);
        return reparation.orElse(null);
    }

    // Delete
    public void deleteReparation(Reparation reparation) {
        reparationRepository.delete(reparation);
    }
}
