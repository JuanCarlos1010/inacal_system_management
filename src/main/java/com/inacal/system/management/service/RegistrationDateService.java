package com.inacal.system.management.service;

import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.FormatVersion;
import com.inacal.system.management.entity.RegistrationDate;
import com.inacal.system.management.repository.RegistrationDateRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationDateService {
    private final RegistrationDateRepository registrationDateRepository;

    public RegistrationDateService(RegistrationDateRepository registrationDateRepository) {
        this.registrationDateRepository = registrationDateRepository;
    }

    PageResponse<RegistrationDate> findAllLaboratories(Pagination pagination) {
        try {
            return registrationDateRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public RegistrationDate findRegistrationDateById(String id) {
        try {
            return registrationDateRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("RegistrationDate not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public RegistrationDate saveRegistrationDate(RegistrationDate body) {
        try {
            body.setId(null);
            FormatVersion formatVersion = body.getFormatVersion();
            if (formatVersion == null) {
                throw new BadRequestException("FormatVersion is required");
            }
            body.setFormatVersion(formatVersion);
            body.setCreatedAt(DateTimeHelper.now());
            return registrationDateRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public RegistrationDate updateRegistrationDate(RegistrationDate body) {
        try {
            body.setId(null);
            return registrationDateRepository.findById(body.getId())
                    .map( registrationDate -> {
                        registrationDate.setRegisterNumber(registrationDate.getRegisterNumber());
                        registrationDate.setCreateDate(registrationDate.getCreateDate());
                        registrationDate.setUpdatedAt(DateTimeHelper.now());
                        return registrationDateRepository.save(body);
                    })
                    .orElseThrow(() -> new NotFoundException("RegistrationDate id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteRegistrationDate(String id) {
        try {
            RegistrationDate registrationDate = findRegistrationDateById(id);
            registrationDate.setDeletedAt(DateTimeHelper.now());
            registrationDateRepository.save(registrationDate);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
