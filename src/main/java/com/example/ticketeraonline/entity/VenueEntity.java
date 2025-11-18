package com.example.ticketeraonline.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "venues")
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private int capacity;
}
