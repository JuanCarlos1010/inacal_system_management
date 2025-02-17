package com.inacal.system.management.service;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.system.management.entity.Form;
import com.inacal.management.model.PageResponse;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.system.management.validator.FormValidator;
import com.inacal.system.management.repository.FormRepository;
import com.inacal.management.exception.InternalServerException;

@Service
public class FormService {
    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public PageResponse<Form> findAllForms(Pagination pagination) {
        try {
            return formRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Form findFormById(String id) {
        try {
            return formRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Form not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Form saveForm(Form body) {
        try {
            body.setId(null);
            FormValidator.validate(body);
            Optional<Form> existingForm = formRepository.findByName(body.getName());
            if (existingForm.isPresent()) {
                    throw new BadRequestException("Form name already exists");
            }
            body.setCreatedAt(LocalDateTime.now());
            return formRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Form updateForm(Form body) {
        try {
            FormValidator.validate(body);
            return formRepository.findById(body.getId())
                    .map( form -> {
                        form.setName(body.getName());
                        form.setUpdatedAt(LocalDateTime.now());
                        return formRepository.save(form);
                    })
                    .orElseThrow(() -> new NotFoundException("Form id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteForm(String id) {
        try {
            Form form = findFormById(id);
            form.setDeletedAt(LocalDateTime.now());
            formRepository.save(form);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
