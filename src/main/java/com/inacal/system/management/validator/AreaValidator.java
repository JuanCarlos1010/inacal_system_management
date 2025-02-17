package com.inacal.system.management.validator;

import com.inacal.system.management.entity.Area;
import com.inacal.management.exception.BadRequestException;

public class AreaValidator {
    public static void validate(Area body) throws BadRequestException {
        if ((body.getName() == null) || (body.getName().trim().isEmpty()))
            throw new BadRequestException("Name is required");
        if (body.getName().length() < 3)
            throw new BadRequestException("Name must be at least 3 characters");
    }
}
