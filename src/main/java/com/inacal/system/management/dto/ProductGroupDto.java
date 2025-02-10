package com.inacal.system.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.inacal.system.management.entity.FormGroup;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGroupDto {
    private String id;
    private String name;
    private String systemName;
    private LocalDateTime createdAt;
    private FormGroup formGroup;
}
