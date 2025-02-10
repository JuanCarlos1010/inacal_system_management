package com.inacal.system.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDto {
    private String id;
    private String name;
    private LocalDateTime createdAt;
}
