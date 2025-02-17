package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.entity.FormatVersion;
import com.inacal.system.management.dto.InternalVersionDto;
import com.inacal.system.management.entity.InternalVersion;

@Component
public class InternalVersionMapper extends AbstractMapper<InternalVersion, InternalVersionDto> {
    @Override
    public InternalVersionDto toDTO(InternalVersion entity) {
        if (entity == null) return null;
        return InternalVersionDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .registerNumber(entity.getRegisterNumber())
                .formatVersionId(entity.getFormatVersion().getId())
                .formatVersionTitle(entity.getFormatVersion().getTitle())
                .formatVersionCode(entity.getFormatVersion().getCode())
                .formatVersionVersion(entity.getFormatVersion().getVersion())
                .build();
    }

    @Override
    public InternalVersion toEntity(InternalVersionDto dto) {
        if (dto == null) return null;
        return InternalVersion.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .registerNumber(dto.getRegisterNumber())
                .formatVersion(dto.getFormatVersionId() != null ? new FormatVersion(dto.getFormatVersionId()) : null)
                .build();
    }
}
