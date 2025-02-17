package com.inacal.system.management.mapper;

import com.inacal.system.management.dto.FormDto;
import com.inacal.system.management.entity.Form;
import org.springframework.stereotype.Component;

@Component
public class FormMapper extends AbstractMapper<Form, FormDto> {
    @Override
    public FormDto toDTO(Form entity) {
        if (entity == null) return null;
        return FormDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .layout(entity.getLayout())
                .createdAt(entity.getCreatedAt())
                .systemName(entity.getSystemName())
                .build();
    }

    @Override
    public Form toEntity(FormDto dto) {
        if (dto == null) return null;
        return Form.builder()
                .id(dto.getId())
                .name(dto.getName())
                .layout(dto.getLayout())
                .createdAt(dto.getCreatedAt())
                .systemName(dto.getSystemName())
                .build();
    }
}
