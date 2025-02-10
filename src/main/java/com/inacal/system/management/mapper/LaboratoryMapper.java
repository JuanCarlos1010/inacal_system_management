package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.LaboratoryDto;
import com.inacal.system.management.entity.Laboratory;

@Mapper(componentModel = "spring")
public interface LaboratoryMapper {
    LaboratoryDto toDTO(Laboratory entity);
    Laboratory toEntity(LaboratoryDto dto);
    List<LaboratoryDto> toDTOList(List<Laboratory> listEntity);
    List<Laboratory> toEntityList(List<LaboratoryDto> ListDto);
}
