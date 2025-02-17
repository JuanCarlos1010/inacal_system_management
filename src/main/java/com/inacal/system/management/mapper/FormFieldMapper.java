package com.inacal.system.management.mapper;

import com.inacal.system.management.entity.Form;
import org.springframework.stereotype.Component;
import com.inacal.system.management.entity.Field;
import com.inacal.system.management.dto.FormFieldDto;
import com.inacal.system.management.entity.FormField;

@Component
public class FormFieldMapper extends AbstractMapper<FormField, FormFieldDto> {
    @Override
    public FormFieldDto toDTO(FormField entity) {
        if (entity == null) return null;
        return FormFieldDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .formId(entity.getForm().getId())
                .formName(entity.getForm().getName())
                .fieldId(entity.getField().getId())
                .fieldName(entity.getField().getName())
                .build();
    }

    @Override
    public FormField toEntity(FormFieldDto dto) {
        if (dto == null) return null;
        return FormField.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .form(dto.getFormId() != null ? new Form(dto.getFormId()) : null)
                .field(dto.getFieldId() != null ? new Field(dto.getFieldId()) : null)
                .build();
    }
}
