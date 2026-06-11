package com.example.leavecalendar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "team_members")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "rotation_position", nullable = false, unique = true)
    private Integer rotationPosition;

    @Column(nullable = false)
    private boolean active = true;

    protected TeamMember() {
    }

    public TeamMember(String name, Integer rotationPosition) {
        this.name = name;
        this.rotationPosition = rotationPosition;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRotationPosition() {
        return rotationPosition;
    }

    public boolean isActive() {
        return active;
    }
}