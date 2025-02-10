package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.ProductDto;
import com.inacal.system.management.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDTO(Product entity);
    Product toEntity(ProductDto dto);
    List<ProductDto> toDTOList(List<Product> listEntity);
    List<Product> toEntityList(List<ProductDto> ListDto);
}
