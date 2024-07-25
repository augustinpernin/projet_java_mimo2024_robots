package com.example.Atelier_de_robots.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Atelier_de_robots.entities.Fabricant;

@Repository
public interface FabricantRepository extends JpaRepository<Fabricant, Long> {
}
