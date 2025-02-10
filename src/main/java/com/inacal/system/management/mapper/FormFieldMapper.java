package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.FormFieldDto;
import com.inacal.system.management.entity.FormField;

@Mapper(componentModel = "spring")
public interface FormFieldMapper {
    FormFieldDto toDTO(FormField entity);
    FormField toEntity(FormFieldDto dto);
    List<FormFieldDto> toDTOList(List<FormField> listEntity);
    List<FormField> toEntityList(List<FormFieldDto> ListDto);
}
