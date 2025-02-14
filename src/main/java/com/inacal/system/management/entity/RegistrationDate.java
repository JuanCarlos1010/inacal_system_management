package com.inacal.system.management.entity;

import lombok.Data;
import java.util.Date;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registration_dates")
public class RegistrationDate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "register_number")
    private double registerNumber;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne
    @JoinColumn(name = "format_version_id")
    private FormatVersion formatVersion;
}
