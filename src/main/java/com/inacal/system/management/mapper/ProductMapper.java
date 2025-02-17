package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.dto.ProductDto;
import com.inacal.system.management.entity.Product;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductDto> {
    @Override
    public ProductDto toDTO(Product entity) {
        if (entity == null) return null;
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .systemName(entity.getSystemName())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public Product toEntity(ProductDto dto) {
        if (dto == null) return null;
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .systemName(dto.getSystemName())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
