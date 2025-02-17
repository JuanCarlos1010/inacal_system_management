package com.inacal.system.management.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.management.model.PageResponse;
import com.inacal.management.exception.NotFoundException;
import com.inacal.system.management.entity.RevisionRecord;
import com.inacal.system.management.entity.InternalVersion;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.validator.RevisionRecordValidator;
import com.inacal.system.management.repository.RevisionRecordRepository;

@Service
public class RevisionRecordService {
    private final RevisionRecordRepository revisionRecordRepository;

    public RevisionRecordService(RevisionRecordRepository revisionRecordRepository) {
        this.revisionRecordRepository = revisionRecordRepository;
    }

    public PageResponse<RevisionRecord> findAllRevisionRecords(Pagination pagination) {
        try {
            return revisionRecordRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public RevisionRecord findRevisionRecordById(String id) {
        try {
            return revisionRecordRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("RevisionRecord not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public RevisionRecord saveRevisionRecord(RevisionRecord body) {
        try {
            body.setId(null);
            RevisionRecordValidator.validate(body);
            InternalVersion internalVersion = body.getInternalVersion();
            if (internalVersion == null) {
                throw new BadRequestException("InternalVersion is required");
            }
            body.setInternalVersion(internalVersion);
            body.setCreatedAt(LocalDateTime.now());
            return revisionRecordRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public RevisionRecord updateRevisionRecord(RevisionRecord body) {
        try {
            RevisionRecordValidator.validate(body);
            return revisionRecordRepository.findById(body.getId())
                    .map( revisionRecord -> {
                        revisionRecord.setPreparedBy(body.getPreparedBy());
                        revisionRecord.setPreparedPosition(body.getPreparedPosition());
                        revisionRecord.setReviewedBy(body.getReviewedBy());
                        revisionRecord.setReviewedPosition(body.getReviewedPosition());
                        revisionRecord.setApprovedBy(body.getApprovedBy());
                        revisionRecord.setApprovedPosition(body.getApprovedPosition());
                        revisionRecord.setUpdatedAt(LocalDateTime.now());
                        return revisionRecordRepository.save(revisionRecord);
                    })
                    .orElseThrow(() -> new NotFoundException("RevisionRecord id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteRevisionRecord(String id) {
        try {
            RevisionRecord revisionRecord = findRevisionRecordById(id);
            revisionRecord.setDeletedAt(LocalDateTime.now());
            revisionRecordRepository.save(revisionRecord);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
