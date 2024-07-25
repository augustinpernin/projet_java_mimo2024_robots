package com.example.Atelier_de_robots.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Atelier_de_robots.entities.Fabricant;
import com.example.Atelier_de_robots.repositories.FabricantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FabricantService {

    @Autowired
    private FabricantRepository fabricantRepository;

    // Create or Update
    public Fabricant createOrUpdateFabricant(Fabricant fabricant) {
        return fabricantRepository.save(fabricant);
    }

    // Read all
    public List<Fabricant> findAllFabricants() {
        return fabricantRepository.findAll();
    }

    // Read by ID
    public Fabricant findFabricantById(Long id) {
        Optional<Fabricant> fabricant = fabricantRepository.findById(id);
        return fabricant.orElse(null);
    }

    // Delete
    public void deleteFabricant(Fabricant fabricant) {
        fabricantRepository.delete(fabricant);
    }
}
