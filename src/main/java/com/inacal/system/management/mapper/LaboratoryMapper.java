package com.inacal.system.management.mapper;

import com.inacal.system.management.entity.Area;
import org.springframework.stereotype.Component;
import com.inacal.system.management.dto.LaboratoryDto;
import com.inacal.system.management.entity.Laboratory;

@Component
public class LaboratoryMapper extends AbstractMapper<Laboratory, LaboratoryDto> {
    @Override
    public LaboratoryDto toDTO(Laboratory entity) {
        if (entity == null) return null;
        return LaboratoryDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .code(entity.getCode())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .areaId(entity.getArea().getId())
                .areaName(entity.getArea().getName())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public Laboratory toEntity(LaboratoryDto dto) {
        if (dto == null) return null;
        return Laboratory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .active(dto.isActive())
                .userId(dto.getUserId())
                .createdAt(dto.getCreatedAt())
                .area(dto.getAreaId() != null? new Area(dto.getAreaId()) : null)
                .description(dto.getDescription())
                .build();
    }
}
