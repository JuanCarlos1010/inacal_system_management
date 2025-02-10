package com.inacal.system.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "data_types")
public class DataType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
}
