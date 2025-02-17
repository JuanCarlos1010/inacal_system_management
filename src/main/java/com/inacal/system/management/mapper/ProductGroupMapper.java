package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.entity.FormGroup;
import com.inacal.system.management.dto.ProductGroupDto;
import com.inacal.system.management.entity.ProductGroup;

@Component
public class ProductGroupMapper extends AbstractMapper<ProductGroup, ProductGroupDto> {
    @Override
    public ProductGroupDto toDTO(ProductGroup entity) {
        if (entity == null) return null;
        return ProductGroupDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .systemName(entity.getSystemName())
                .formGroupId(entity.getFormGroup().getId())
                .formGroupName(entity.getFormGroup().getName())
                .build();
    }

    @Override
    public ProductGroup toEntity(ProductGroupDto dto) {
        if (dto == null) return null;
        return ProductGroup.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createdAt(dto.getCreatedAt())
                .systemName(dto.getSystemName())
                .formGroup(dto.getFormGroupId() != null ? new FormGroup(dto.getFormGroupId()) : null)
                .build();
    }
}
