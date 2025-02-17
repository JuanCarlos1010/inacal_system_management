package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.dto.FormatVersionDto;
import com.inacal.system.management.entity.FormatVersion;

@Component
public class FormatVersionMapper extends AbstractMapper<FormatVersion, FormatVersionDto> {
    @Override
    public FormatVersionDto toDTO(FormatVersion entity) {
        if (entity == null) return null;
        return FormatVersionDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .title(entity.getTitle())
                .version(entity.getVersion())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public FormatVersion toEntity(FormatVersionDto dto) {
        if (dto == null) return null;
        return FormatVersion.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .title(dto.getTitle())
                .version(dto.getVersion())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
