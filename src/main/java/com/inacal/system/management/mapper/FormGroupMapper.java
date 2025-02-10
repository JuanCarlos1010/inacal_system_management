package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.FormGroupDto;
import com.inacal.system.management.entity.FormGroup;

@Mapper(componentModel = "spring")
public interface FormGroupMapper {
    FormGroupDto toDTO(FormGroup entity);
    FormGroup toEntity(FormGroupDto dto);
    List<FormGroupDto> toDTOList(List<FormGroup> listEntity);
    List<FormGroup> toEntityList(List<FormGroupDto> ListDto);
}
