package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.dto.DataTypeDto;
import com.inacal.system.management.entity.DataType;

@Component
public class DataTypeMapper extends AbstractMapper<DataType, DataTypeDto> {
    @Override
    public DataTypeDto toDTO(DataType entity) {
        if (entity == null) return null;
        return DataTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public DataType toEntity(DataTypeDto dto) {
        if (dto == null) return null;
        return DataType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
