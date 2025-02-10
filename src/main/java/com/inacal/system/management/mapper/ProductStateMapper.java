package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.ProductStateDto;
import com.inacal.system.management.entity.ProductState;

@Mapper(componentModel = "spring")
public interface ProductStateMapper {
    ProductStateDto toDTO(ProductState entity);
    ProductState toEntity(ProductStateDto dto);
    List<ProductStateDto> toDTOList(List<ProductState> listEntity);
    List<ProductState> toEntityList(List<ProductStateDto> ListDto);
}
