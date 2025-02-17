package com.inacal.system.management.service;

import java.time.LocalDateTime;
import com.inacal.management.model.Pagination;
import org.springframework.stereotype.Service;
import com.inacal.management.model.PageResponse;
import com.inacal.management.exception.NotFoundException;
import com.inacal.system.management.entity.FormatVersion;
import com.inacal.system.management.entity.InternalVersion;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.validator.InternalVersionValidator;
import com.inacal.system.management.repository.InternalVersionRepository;

@Service
public class InternalVersionService {
    private final InternalVersionRepository internalVersionRepository;

    public InternalVersionService(InternalVersionRepository internalVersionRepository) {
        this.internalVersionRepository = internalVersionRepository;
    }

    public PageResponse<InternalVersion> findAllRegistrationDates(Pagination pagination) {
        try {
            return internalVersionRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public InternalVersion findRegistrationDateById(String id) {
        try {
            return internalVersionRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("InternalVersion not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public InternalVersion saveRegistrationDate(InternalVersion body) {
        try {
            body.setId(null);
            InternalVersionValidator.validate(body);
            FormatVersion formatVersion = body.getFormatVersion();
            if (formatVersion == null) {
                throw new BadRequestException("InternalVersion is required");
            }
            body.setFormatVersion(formatVersion);
            body.setCreatedAt(LocalDateTime.now());
            return internalVersionRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public InternalVersion updateRegistrationDate(InternalVersion body) {
        try {
            InternalVersionValidator.validate(body);
            return internalVersionRepository.findById(body.getId())
                    .map( internalVersion -> {
                        internalVersion.setRegisterNumber(internalVersion.getRegisterNumber());
                        internalVersion.setUpdatedAt(LocalDateTime.now());
                        return internalVersionRepository.save(internalVersion);
                    })
                    .orElseThrow(() -> new NotFoundException("InternalVersion id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteRegistrationDate(String id) {
        try {
            InternalVersion internalVersion = findRegistrationDateById(id);
            internalVersion.setDeletedAt(LocalDateTime.now());
            internalVersionRepository.save(internalVersion);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
