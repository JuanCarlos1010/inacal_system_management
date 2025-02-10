package com.inacal.system.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.inacal.system.management.entity.BusinessData;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    private String id;
    private String name;
    private String label;
    private String systemName;
    private String properties;
    private LocalDateTime createdAt;
    private BusinessData businessData;
}
