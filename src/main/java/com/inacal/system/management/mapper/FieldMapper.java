package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.dto.FieldDto;
import com.inacal.system.management.entity.Field;
import com.inacal.system.management.entity.BusinessData;

@Component
public class FieldMapper extends AbstractMapper<Field, FieldDto> {
    @Override
    public FieldDto toDTO(Field entity) {
        if (entity == null) return null;
        return FieldDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .label(entity.getLabel())
                .createdAt(entity.getCreatedAt())
                .properties(entity.getProperties())
                .systemName(entity.getSystemName())
                .businessDataId(entity.getBusinessData().getId())
                .businessDataName(entity.getBusinessData().getName())
                .build();
    }

    @Override
    public Field toEntity(FieldDto dto) {
        if (dto == null) return null;
        return Field.builder()
                .id(dto.getId())
                .name(dto.getName())
                .label(dto.getLabel())
                .createdAt(dto.getCreatedAt())
                .systemName(dto.getSystemName())
                .properties(dto.getProperties())
                .businessData(dto.getBusinessDataId() != null ? new BusinessData(dto.getBusinessDataId()) : null)
                .build();
    }
}
