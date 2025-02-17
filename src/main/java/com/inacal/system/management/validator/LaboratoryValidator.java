package com.inacal.system.management.validator;

import com.inacal.system.management.entity.Laboratory;
import com.inacal.management.exception.BadRequestException;

public class LaboratoryValidator {
    public static void validate(Laboratory body) throws BadRequestException {
        if ((body.getName() == null) || (body.getName().trim().isEmpty()))
            throw new BadRequestException("Name is required");
        if (body.getName().length() < 3)
            throw new BadRequestException("Name must be at least 3 characters");
        if (body.getCode() == null)
            throw new BadRequestException("Code is required");
        if (body.getCode().length() < 2)
            throw new BadRequestException("Code must be at least 2 characters");
        if (isLowercase(body.getCode()))
            throw new BadRequestException("Code contains lowercase characters");
    }

    public static boolean isLowercase(String input) {
        return !input.isEmpty() && input.chars().allMatch(Character::isLowerCase);
    }
}
