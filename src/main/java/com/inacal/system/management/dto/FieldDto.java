package com.inacal.system.management.dto;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    private String id;
    private String name;
    private String label;
    private String systemName;
    private JsonNode properties;
    private LocalDateTime createdAt;
    private String businessDataId;
    private String businessDataName;
}
