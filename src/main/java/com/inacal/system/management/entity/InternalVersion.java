package com.inacal.system.management.entity;

import lombok.Data;
import lombok.Builder;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "internal_versions")
public class InternalVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "register_number")
    private double registerNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne
    @JoinColumn(name = "format_version_id")
    private FormatVersion formatVersion;

    public InternalVersion(String internalVersionId) {
        this.id = internalVersionId;
    }
}
