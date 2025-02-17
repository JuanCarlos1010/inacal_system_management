package com.inacal.system.management.validator;

import com.inacal.system.management.entity.FormGroup;
import com.inacal.management.exception.BadRequestException;

public class FormGroupValidator {
    public static void validate(FormGroup body) throws BadRequestException {
        if ((body.getName() == null) || (body.getName().trim().isEmpty()))
            throw new BadRequestException("Name is required");
        if (body.getName().length() < 5)
            throw new BadRequestException("Name must be at least 5 characters");
    }
}
