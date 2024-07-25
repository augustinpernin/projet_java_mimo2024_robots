package com.example.Atelier_de_robots.entities;

import javax.persistence.*;

@Entity
public class PartieRobot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // Ex: "Bras", "Jambe", "Torse", "Tête", "Puce_électronique", "Batterie"

    @ManyToOne
    @JoinColumn(name = "id_robot")
    private Robot robot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

}
