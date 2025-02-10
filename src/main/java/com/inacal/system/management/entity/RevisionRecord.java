package com.inacal.system.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "revision_records")
public class RevisionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "last_update")
    private Date lastUpdate;

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
    @JoinColumn(name = "registration_date_id")
    private RegistrationDate registrationDate;
}
