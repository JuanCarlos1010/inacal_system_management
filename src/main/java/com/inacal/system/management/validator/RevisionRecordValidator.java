package com.inacal.system.management.validator;

import com.inacal.system.management.entity.RevisionRecord;
import com.inacal.management.exception.BadRequestException;

public class RevisionRecordValidator {
    public static void validate(RevisionRecord body) throws BadRequestException {
        if ((body.getPreparedBy() == null)
                || (body.getPreparedBy().trim().isEmpty())
                || (body.getPreparedBy().length() < 10))
            throw new BadRequestException("PreparedBy is required and must be at least 10 characters long");
        if ((body.getPreparedPosition() == null)
                || (body.getPreparedPosition().trim().isEmpty())
                || (body.getPreparedPosition().length() < 10))
            throw new BadRequestException("PreparedPosition is required and must be at least 10 characters long");
        if ((body.getReviewedBy() == null)
                || (body.getReviewedBy().trim().isEmpty())
                || (body.getReviewedBy().length() < 10))
            throw new BadRequestException("PreparedPosition is required and must be at least 10 characters long");
        if ((body.getReviewedPosition() == null)
                || (body.getReviewedPosition().trim().isEmpty())
                || (body.getReviewedPosition().length() < 10))
            throw new BadRequestException("ReviewedPosition is required and must be at least 10 characters long");
        if ((body.getApprovedBy() == null)
                || (body.getApprovedBy().trim().isEmpty())
                || (body.getApprovedBy().length() < 10))
            throw new BadRequestException("PreparedPosition is required and must be at least 10 characters long");
        if ((body.getApprovedPosition() == null)
                || (body.getApprovedPosition().trim().isEmpty())
                || (body.getApprovedPosition().length() < 10))
            throw new BadRequestException("ApprovedPosition is required and must be at least 10 characters long");
    }
}
