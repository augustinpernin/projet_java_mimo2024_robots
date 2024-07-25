package com.example.Atelier_de_robots.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Atelier_de_robots.entities.Robot;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Long> {
    int countByModele(String modele);
    int count();
}
