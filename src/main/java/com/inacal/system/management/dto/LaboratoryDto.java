package com.inacal.system.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.inacal.system.management.entity.Area;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaboratoryDto {
    private String id;
    private String name;
    private String code;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Area area;
}
