package com.inacal.system.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.inacal.system.management.entity.Form;
import com.inacal.system.management.entity.FormatVersion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormGroupDto {
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Form form;
    private FormatVersion formatVersion;
}
