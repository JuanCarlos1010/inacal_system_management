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
@Table(name = "revision_records")
public class RevisionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "prepared_by")
    private String preparedBy;

    @Column(name = "prepared_position")
    private String preparedPosition;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    @Column(name = "reviewed_position")
    private String reviewedPosition;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_position")
    private String approvedPosition;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne
    @JoinColumn(name = "internal_version_id")
    private InternalVersion internalVersion;
}
