package com.inacal.system.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_groups")
public class ProductGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(name = "system_name")
    private String systemName;

    @OneToOne
    @JoinColumn(name = "form_group_id")
    private  FormGroup formGroup;
}
