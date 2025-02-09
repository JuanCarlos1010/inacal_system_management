package com.inacal.system.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "data_types")
public class DataType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
}
