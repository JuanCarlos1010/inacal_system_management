package com.inacal.system.management.dto;

import lombok.Data;
import java.util.Date;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.inacal.system.management.entity.FormatVersion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDateDto {
    private String id;
    private double registerNumber;
    private Date createDate;
    private LocalDateTime createdAt;
    private FormatVersion formatVersion;
}
