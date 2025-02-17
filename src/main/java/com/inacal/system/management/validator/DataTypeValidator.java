package com.inacal.system.management.validator;

import com.inacal.system.management.entity.DataType;
import com.inacal.management.exception.BadRequestException;

public class DataTypeValidator {
    public static void validate(DataType body) throws BadRequestException {
        if ((body.getName() == null)
                || (body.getName().trim().isEmpty())
                || (body.getName().length() < 5 ))
            throw new BadRequestException("Name is required and must be at least 5 characters long");
    }
}
