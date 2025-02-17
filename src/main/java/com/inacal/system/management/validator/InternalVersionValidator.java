package com.inacal.system.management.validator;

import com.inacal.system.management.entity.InternalVersion;
import com.inacal.management.exception.BadRequestException;

public class InternalVersionValidator {
    public static void validate(InternalVersion body) throws BadRequestException {
        if (body.getRegisterNumber() < 1)
            throw new BadRequestException("RegisterNumber must not be less than one");
    }
}
