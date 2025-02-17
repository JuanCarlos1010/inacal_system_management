package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.entity.DataType;
import com.inacal.system.management.dto.BusinessDataDto;
import com.inacal.system.management.entity.BusinessData;

@Component
public class BusinessDataMapper extends AbstractMapper<BusinessData, BusinessDataDto> {
    @Override
    public BusinessDataDto toDTO(BusinessData entity) {
        if (entity == null) return null;
        return BusinessDataDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .systemName(entity.getSystemName())
                .dataTypeId(entity.getDataType().getId())
                .build();
    }

    @Override
    public BusinessData toEntity(BusinessDataDto dto) {
        if (dto == null) return null;
        return BusinessData.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createdAt(dto.getCreatedAt())
                .systemName(dto.getSystemName())
                .dataType(new DataType(dto.getDataTypeId()))
                .build();
    }
}
