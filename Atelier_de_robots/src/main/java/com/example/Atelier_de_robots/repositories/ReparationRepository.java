package com.example.Atelier_de_robots.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Atelier_de_robots.entities.Reparation;
import com.example.Atelier_de_robots.entities.Robot;

import java.time.LocalDate;


@Repository
public interface ReparationRepository extends JpaRepository<Reparation, Long> {
    long countByRobotAndDateReparationBetween(Robot robot, LocalDate startDate, LocalDate endDate);
}
