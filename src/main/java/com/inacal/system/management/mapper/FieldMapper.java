package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.FieldDto;
import com.inacal.system.management.entity.Field;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldDto toDTO(Field entity);
    Field toEntity(FieldDto dto);
    List<FieldDto> toDTOList(List<Field> listEntity);
    List<Field> toEntityList(List<FieldDto> ListDto);
}
