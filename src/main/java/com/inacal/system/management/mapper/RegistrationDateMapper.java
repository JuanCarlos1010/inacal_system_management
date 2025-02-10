package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.RegistrationDateDto;
import com.inacal.system.management.entity.RegistrationDate;

@Mapper(componentModel = "spring")
public interface RegistrationDateMapper {
    RegistrationDateDto toDTO(RegistrationDate entity);
    RegistrationDate toEntity(RegistrationDateDto dto);
    List<RegistrationDateDto> toDTOList(List<RegistrationDate> listEntity);
    List<RegistrationDate> toEntityList(List<RegistrationDateDto> ListDto);
}
