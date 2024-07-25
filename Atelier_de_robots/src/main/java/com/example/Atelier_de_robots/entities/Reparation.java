package com.example.Atelier_de_robots.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Reparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_robot")
    private Robot robot;

    @Column(nullable = false)
    private LocalDate dateReparation;

    private String description;
    private BigDecimal cout;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public LocalDate getDateReparation() {
        return dateReparation;
    }

    public void setDateReparation(LocalDate dateReparation) {
        if (dateReparation.isBefore(this.robot.getDateFabrication())) {
            throw new IllegalArgumentException("La date de réparation doit être postérieure à la date de fabrication du robot.");
        }
        this.dateReparation = dateReparation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCout() {
        return cout;
    }

    public void setCout(BigDecimal cout) {
        this.cout = cout;
    }
}
