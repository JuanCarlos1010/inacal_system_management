package com.inacal.system.management.mapper;

import com.inacal.system.management.dto.AreaDto;
import com.inacal.system.management.entity.Area;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper extends AbstractMapper<Area, AreaDto> {
    @Override
    public AreaDto toDTO(Area entity) {
        if (entity == null) return null;
        return AreaDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public Area toEntity(AreaDto dto) {
        if (dto == null) return null;
        return Area.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
