package com.inacal.system.management.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.management.model.PageResponse;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.Field;
import com.inacal.system.management.entity.BusinessData;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.repository.FieldRepository;

@Service
public class FieldService {
    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public PageResponse<Field> findAllFields(Pagination pagination) {
        try {
            return fieldRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Field findFieldById(String id) {
        try {
            return fieldRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Field not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Field saveField(Field body) {
        try {
            body.setId(null);
            BusinessData businessData = body.getBusinessData();
            if (businessData == null) {
                throw new BadRequestException("BusinessData is required");
            } else {
                Optional<Field> existingField = fieldRepository.findByName(body.getName());
                if (existingField.isPresent()) {
                    throw new BadRequestException("Field name already exists");
                }
            }
            body.setBusinessData(businessData);
            body.setCreatedAt(DateTimeHelper.now());
            return fieldRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Field updateField(Field body) {
        try {
            body.setId(null);
            return fieldRepository.findById(body.getId())
                    .map( field -> {
                        field.setName(body.getName());
                        field.setLabel(body.getLabel());
                        field.setSystemName(body.getSystemName());
                        field.setProperties(body.getProperties());
                        field.setUpdatedAt(DateTimeHelper.now());
                        return fieldRepository.save(body);
                    })
                    .orElseThrow(() -> new NotFoundException("Field id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteField(String id) {
        try {
            Field field = findFieldById(id);
            field.setDeletedAt(DateTimeHelper.now());
            fieldRepository.save(field);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
