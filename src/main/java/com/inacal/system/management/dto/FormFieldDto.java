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
public class FormFieldDto {
    private String id;
    private LocalDateTime createdAt;
    private String formId;
    private String formName;
    private String fieldId;
    private String fieldName;
}
