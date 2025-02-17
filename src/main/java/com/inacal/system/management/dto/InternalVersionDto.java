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
public class InternalVersionDto {
    private String id;
    private double registerNumber;
    private LocalDateTime createdAt;
    private String formatVersionId;
    private String formatVersionTitle;
    private String formatVersionCode;
    private double formatVersionVersion;
}
