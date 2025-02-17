package com.inacal.system.management.validator;

import com.inacal.system.management.entity.ProductGroup;
import com.inacal.management.exception.BadRequestException;

public class ProductGroupValidator {
    public static void validate(ProductGroup body) throws BadRequestException {
        if ((body.getName() == null) || (body.getName().trim().isEmpty()))
            throw new BadRequestException("Name is required");
        if (body.getName().length() < 3)
            throw new BadRequestException("Name must be at least 3 characters");
        if ((body.getSystemName() == null) || (body.getSystemName().trim().isEmpty()))
            throw new BadRequestException("SystemName is required");
        if (body.getSystemName().length() < 3)
            throw new BadRequestException("SystemName must be at least 3 characters");
    }
}
