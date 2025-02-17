package com.inacal.system.management.dto;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormGroupDto {
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String formId;
    private String formatVersionId;
    private String formatVersionName;
}
