package com.inacal.system.management.dto;

import lombok.Data;
import java.util.Date;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.inacal.system.management.entity.RegistrationDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevisionRecordDto {
    private String id;
    private Date lastUpdate;
    private String preparedBy;
    private String preparedPosition;
    private String reviewedBy;
    private String reviewedPosition;
    private String approvedBy;
    private String approvedPosition;
    private LocalDateTime createdAt;
    private RegistrationDate registrationDate;
}
