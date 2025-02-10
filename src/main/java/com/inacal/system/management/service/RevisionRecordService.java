package com.inacal.system.management.service;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.management.model.PageResponse;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.management.exception.NotFoundException;
import com.inacal.system.management.entity.RevisionRecord;
import com.inacal.management.exception.BadRequestException;
import com.inacal.system.management.entity.RegistrationDate;
import com.inacal.management.exception.InternalServerException;
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
            RegistrationDate registrationDate = body.getRegistrationDate();
            if (registrationDate == null) {
                throw new BadRequestException("RegistrationDate is required");
            }
            body.setRegistrationDate(registrationDate);
            body.setCreatedAt(DateTimeHelper.now());
            return revisionRecordRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public RevisionRecord updateRevisionRecord(RevisionRecord body) {
        try {
            body.setId(null);
            return revisionRecordRepository.findById(body.getId())
                    .map( revisionRecord -> {
                        revisionRecord.setLastUpdate(new Date());
                        revisionRecord.setPreparedBy(body.getPreparedBy());
                        revisionRecord.setPreparedPosition(body.getPreparedPosition());
                        revisionRecord.setReviewedBy(body.getReviewedBy());
                        revisionRecord.setReviewedPosition(body.getReviewedPosition());
                        revisionRecord.setApprovedBy(revisionRecord.getApprovedBy());
                        revisionRecord.setApprovedPosition(revisionRecord.getApprovedPosition());
                        revisionRecord.setUpdatedAt(DateTimeHelper.now());
                        return revisionRecordRepository.save(body);
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
            revisionRecord.setDeletedAt(DateTimeHelper.now());
            revisionRecordRepository.save(revisionRecord);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
