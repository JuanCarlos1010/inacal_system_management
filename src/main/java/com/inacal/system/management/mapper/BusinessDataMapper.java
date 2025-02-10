package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.BusinessDataDto;
import com.inacal.system.management.entity.BusinessData;

@Mapper(componentModel = "spring")
public interface BusinessDataMapper {
    BusinessDataDto toDTO(BusinessData entity);
    BusinessData toEntity(BusinessDataDto dto);
    List<BusinessDataDto> toDTOList(List<BusinessData> listEntity);
    List<BusinessData> toEntityList(List<BusinessDataDto> ListDto);
}
