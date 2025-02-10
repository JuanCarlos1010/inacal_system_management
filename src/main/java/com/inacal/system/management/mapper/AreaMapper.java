package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.AreaDto;
import com.inacal.system.management.entity.Area;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    AreaDto toDTO(Area entity);
    Area toEntity(AreaDto dto);
    List<AreaDto> toDTOList(List<Area> listEntity);
    List<Area> toEntityList(List<AreaDto> ListDto);
}
