package com.inacal.system.management.service;

import java.time.LocalDateTime;
import com.inacal.management.model.Pagination;
import org.springframework.stereotype.Service;
import com.inacal.system.management.entity.Form;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.entity.Field;
import com.inacal.system.management.entity.FormField;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.repository.FormFieldRepository;

@Service
public class FormFieldService {
    private final FormFieldRepository formFieldRepository;

    public FormFieldService(FormFieldRepository formFieldRepository) {
        this.formFieldRepository = formFieldRepository;
    }

    public PageResponse<FormField> findAllFormsField(Pagination pagination) {
        try {
            return formFieldRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormField findFormFieldById(String id) {
        try {
            return formFieldRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("FormField not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormField saveFormField(FormField body) {
        try {
            body.setId(null);
            Form form = body.getForm();
            Field field = body.getField();
            if ((form == null) || (field == null)) {
                throw new BadRequestException("Form and Field is required");
            }
            body.setForm(form);
            body.setField(field);
            body.setCreatedAt(LocalDateTime.now());
            return formFieldRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteFormField(String id) {
        try {
            FormField formField = findFormFieldById(id);
            formField.setDeletedAt(LocalDateTime.now());
            formFieldRepository.save(formField);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
