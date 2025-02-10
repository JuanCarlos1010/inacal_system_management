package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.ProductGroupDto;
import com.inacal.system.management.entity.ProductGroup;

@Mapper(componentModel = "spring")
public interface ProductGroupMapper {
    ProductGroupDto toDTO(ProductGroup entity);
    ProductGroup toEntity(ProductGroupDto dto);
    List<ProductGroupDto> toDTOList(List<ProductGroup> listEntity);
    List<ProductGroup> toEntityList(List<ProductGroupDto> ListDto);
}
