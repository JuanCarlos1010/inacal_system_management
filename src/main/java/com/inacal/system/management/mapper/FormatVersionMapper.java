package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.FormatVersionDto;
import com.inacal.system.management.entity.FormatVersion;

@Mapper(componentModel = "spring")
public interface FormatVersionMapper {
    FormatVersionDto toDTO(FormatVersion entity);
    FormatVersion toEntity(FormatVersionDto dto);
    List<FormatVersionDto> toDTOList(List<FormatVersion> listEntity);
    List<FormatVersion> toEntityList(List<FormatVersionDto> ListDto);
}
