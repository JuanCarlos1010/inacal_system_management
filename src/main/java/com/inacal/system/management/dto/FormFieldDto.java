package com.inacal.system.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.inacal.system.management.entity.Field;
import com.inacal.system.management.entity.Form;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldDto {
    private String id;
    private LocalDateTime createdAt;
    private Form form;
    private Field field;
}
