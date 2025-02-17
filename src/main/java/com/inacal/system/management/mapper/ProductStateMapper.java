package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.dto.ProductStateDto;
import com.inacal.system.management.entity.ProductState;

@Component
public class ProductStateMapper extends AbstractMapper<ProductState, ProductStateDto> {
    @Override
    public ProductStateDto toDTO(ProductState entity) {
        if (entity == null) return null;
        return ProductStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .systemName(entity.getSystemName())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public ProductState toEntity(ProductStateDto dto) {
        if (dto == null) return null;
        return ProductState.builder()
                .id(dto.getId())
                .name(dto.getName())
                .systemName(dto.getSystemName())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
