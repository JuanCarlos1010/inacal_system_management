package com.inacal.system.management.validator;

import com.inacal.system.management.entity.FormatVersion;
import com.inacal.management.exception.BadRequestException;

public class FormatVersionValidator {
    public static void validate(FormatVersion body) throws BadRequestException {
        if ((body.getTitle() == null) || (body.getTitle().trim().isEmpty()))
            throw new BadRequestException("Title is required");
        if (body.getCode() == null)
            throw new BadRequestException("Code is required");
        if (body.getCode().length() < 7)
            throw new BadRequestException("Code must be at least 7 characters and ");
        if (body.getVersion() < 1.0)
            throw new BadRequestException("Version must not be less than one");
    }
}
