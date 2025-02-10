package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.FormDto;
import com.inacal.system.management.entity.Form;

@Mapper(componentModel = "spring")
public interface FormMapper {
    FormDto toDTO(Form entity);
    Form toEntity(FormDto dto);
    List<FormDto> toDTOList(List<Form> listEntity);
    List<Form> toEntityList(List<FormDto> ListDto);
}
