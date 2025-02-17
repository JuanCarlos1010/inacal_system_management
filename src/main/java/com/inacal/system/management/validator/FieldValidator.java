package com.inacal.system.management.validator;

import com.inacal.system.management.entity.Field;
import com.inacal.management.exception.BadRequestException;

public class FieldValidator {
    public static void validate(Field body) throws BadRequestException {
        if ((body.getName() == null) || (body.getName().trim().isEmpty()))
            throw new BadRequestException("Name is required");
        if (body.getName().length() < 3)
            throw new BadRequestException("Name must be at least 3 characters");
        if ((body.getSystemName() == null) || (body.getSystemName().trim().isEmpty()))
            throw new BadRequestException("SystemName is required");
        if (body.getSystemName().length() < 3)
            throw new BadRequestException("SystemName must be at least 3 characters");
        if ((body.getLabel() == null) || (body.getLabel().trim().isEmpty()))
            throw new BadRequestException("Label is required");
        if (body.getLabel().length() < 3)
            throw new BadRequestException("Label must be at least 3 characters");
        if ((body.getProperties() == null) || (body.getProperties().isEmpty())) {
            throw new BadRequestException("Properties is required");
        }
    }
}
