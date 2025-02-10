package com.inacal.system.management.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "data_types")
public class DataType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
}
