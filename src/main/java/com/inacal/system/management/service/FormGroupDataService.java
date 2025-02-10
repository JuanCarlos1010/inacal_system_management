package com.inacal.system.management.service;

import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.Form;
import com.inacal.system.management.entity.FormGroup;
import com.inacal.system.management.entity.FormatVersion;
import com.inacal.system.management.repository.FormGroupRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FormGroupDataService {
    private final FormGroupRepository formGroupRepository;

    public FormGroupDataService(FormGroupRepository formGroupRepository) {
        this.formGroupRepository = formGroupRepository;
    }

    PageResponse<FormGroup> findAllFormGroup(Pagination pagination) {
        try {
            return formGroupRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormGroup findFormGroupById(String id) {
        try {
            return formGroupRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("FormGroup not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormGroup saveFormGroup(FormGroup body) {
        try {
            body.setId(null);
            Form form = body.getForm();
            FormatVersion formatVersion = body.getFormatVersion();
            if ((form == null) || (formatVersion == null)) {
                throw new BadRequestException("Form and FormatVersion is required");
            } else {
                Optional<FormGroup> existingFormGroup = formGroupRepository.findByName(body.getName());
                if (existingFormGroup.isPresent()) {
                    throw new BadRequestException("FormGroup name already exists");
                }
            }
            body.setForm(form);
            body.setFormatVersion(formatVersion);
            body.setCreatedAt(DateTimeHelper.now());
            return formGroupRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormGroup updateFormGroup(FormGroup body) {
        try {
            body.setId(null);
            return formGroupRepository.findById(body.getId())
                    .map( formGroup -> {
                        formGroup.setName(body.getName());
                        formGroup.setDescription(body.getDescription());
                        formGroup.setUpdatedAt(DateTimeHelper.now());
                        return formGroupRepository.save(body);
                    })
                    .orElseThrow(() -> new NotFoundException("FormGroup id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteFormGroup(String id) {
        try {
            FormGroup formGroup = findFormGroupById(id);
            formGroup.setDeletedAt(DateTimeHelper.now());
            formGroupRepository.save(formGroup);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
