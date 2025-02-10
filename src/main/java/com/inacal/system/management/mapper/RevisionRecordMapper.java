package com.inacal.system.management.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.inacal.system.management.dto.RevisionRecordDto;
import com.inacal.system.management.entity.RevisionRecord;

@Mapper(componentModel = "spring")
public interface RevisionRecordMapper {
    RevisionRecordDto toDTO(RevisionRecord entity);
    RevisionRecord toEntity(RevisionRecordDto dto);
    List<RevisionRecordDto> toDTOList(List<RevisionRecord> listEntity);
    List<RevisionRecord> toEntityList(List<RevisionRecordDto> ListDto);
}
