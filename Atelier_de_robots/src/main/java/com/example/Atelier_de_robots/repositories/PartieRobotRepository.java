package com.example.Atelier_de_robots.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Atelier_de_robots.entities.PartieRobot;

@Repository
public interface PartieRobotRepository extends JpaRepository<PartieRobot, Long> {
}
