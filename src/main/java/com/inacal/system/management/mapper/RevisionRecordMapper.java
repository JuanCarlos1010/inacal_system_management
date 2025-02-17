package com.inacal.system.management.mapper;

import com.inacal.system.management.dto.RevisionRecordDto;
import com.inacal.system.management.entity.InternalVersion;
import com.inacal.system.management.entity.RevisionRecord;
import org.springframework.stereotype.Component;

@Component
public class RevisionRecordMapper extends AbstractMapper<RevisionRecord, RevisionRecordDto> {
    @Override
    public RevisionRecordDto toDTO(RevisionRecord entity) {
        if (entity == null) return null;
        return RevisionRecordDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .preparedBy(entity.getPreparedBy())
                .reviewedBy(entity.getReviewedBy())
                .approvedBy(entity.getApprovedBy())
                .preparedPosition(entity.getPreparedPosition())
                .reviewedPosition(entity.getReviewedPosition())
                .approvedPosition(entity.getApprovedPosition())
                .internalVersionId(entity.getInternalVersion().getId())
                .build();
    }

    @Override
    public RevisionRecord toEntity(RevisionRecordDto dto) {
        if (dto == null) return null;
        return RevisionRecord.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .preparedBy(dto.getPreparedBy())
                .reviewedBy(dto.getReviewedBy())
                .approvedBy(dto.getApprovedBy())
                .preparedPosition(dto.getPreparedPosition())
                .reviewedPosition(dto.getReviewedPosition())
                .approvedPosition(dto.getApprovedPosition())
                .internalVersion(dto.getInternalVersionId() != null ? new InternalVersion(dto.getInternalVersionId()): null)
                .build();
    }
}
