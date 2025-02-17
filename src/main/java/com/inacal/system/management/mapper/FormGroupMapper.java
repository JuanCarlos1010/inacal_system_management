package com.inacal.system.management.mapper;

import org.springframework.stereotype.Component;
import com.inacal.system.management.entity.Form;
import com.inacal.system.management.dto.FormGroupDto;
import com.inacal.system.management.entity.FormGroup;
import com.inacal.system.management.entity.FormatVersion;

@Component
public class FormGroupMapper extends AbstractMapper<FormGroup, FormGroupDto> {
    @Override
    public FormGroupDto toDTO(FormGroup entity) {
        if (entity == null) return null;
        return FormGroupDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .formId(entity.getForm().getId())
                .description(entity.getDescription())
                .formatVersionName(entity.getForm().getName())
                .formatVersionId(entity.getFormatVersion().getId())
                .build();
    }

    @Override
    public FormGroup toEntity(FormGroupDto dto) {
        if (dto == null) return null;
        return FormGroup.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createdAt(dto.getCreatedAt())
                .description(dto.getDescription())
                .form(dto.getFormId() != null ? new Form(dto.getFormId()) : null)
                .formatVersion(dto.getFormatVersionId() != null ? new FormatVersion(dto.getFormatVersionId()) : null)
                .build();
    }
}
