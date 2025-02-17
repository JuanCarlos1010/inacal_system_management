package com.inacal.system.management.mapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<Entity, DTO> {
    public abstract DTO toDTO(Entity entity);
    public abstract Entity toEntity(DTO dto);

    public List<DTO> toDTO(List<Entity> entities){
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Entity> toEntity(List<DTO> dtoList){
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}