package com.inacal.system.management.dto;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionRecordDto {
    private String id;
    private String preparedBy;
    private String preparedPosition;
    private String reviewedBy;
    private String reviewedPosition;
    private String approvedBy;
    private String approvedPosition;
    private LocalDateTime createdAt;
    private String internalVersionId;
}
