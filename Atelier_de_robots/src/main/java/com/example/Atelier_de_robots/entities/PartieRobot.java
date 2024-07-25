package com.example.Atelier_de_robots.entities;

import jakarta.persistence.*;

@Entity
public class PartieRobot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypePartie type;

    @ManyToOne
    @JoinColumn(name = "id_robot")
    private Robot robot;

    // Constructor
    public PartieRobot() {}

    public PartieRobot(TypePartie type, Robot robot) {
        this.type = type;
        this.robot = robot;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePartie getType() {
        return type;
    }

    public void setType(TypePartie type) {
        this.type = type;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
}