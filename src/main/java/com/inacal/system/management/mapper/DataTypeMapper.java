package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.DataTypeDto;
import com.inacal.system.management.entity.DataType;

@Mapper(componentModel = "spring")
public interface DataTypeMapper {
    DataTypeDto toDTO(DataType entity);
    DataType toEntity(DataTypeDto dto);
    List<DataTypeDto> toDTOList(List<DataType> listEntity);
    List<DataType> toEntityList(List<DataTypeDto> ListDto);
}
