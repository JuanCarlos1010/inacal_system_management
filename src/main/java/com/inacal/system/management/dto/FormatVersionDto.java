package com.inacal.system.management.dto;

import lombok.Data;
import java.util.Date;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormatVersionDto {
    private String id;
    private String title;
    private String code;
    private double version;
    private Date createDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
