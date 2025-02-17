package com.inacal.system.management.service;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.entity.Area;
import com.inacal.system.management.entity.Laboratory;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.validator.LaboratoryValidator;
import com.inacal.system.management.repository.LaboratoryRepository;

@Service
public class LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryService(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    public PageResponse<Laboratory> findAllLaboratories(Pagination pagination) {
        try {
            return laboratoryRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Laboratory findLaboratoryById(String id) {
        try {
            return laboratoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Laboratory not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Laboratory saveLaboratory(Laboratory body) {
        try {
            body.setId(null);
            LaboratoryValidator.validate(body);
            Area area = body.getArea();
            if (area == null) {
                throw new BadRequestException("Area is required");
            } else {
                Optional<Laboratory> existingLaboratory = laboratoryRepository.findByName(body.getName());
                if (existingLaboratory.isPresent()) {
                    throw new BadRequestException("Laboratory name already exists");
                }
            }
            body.setArea(area);
            body.setCreatedAt(LocalDateTime.now());
            return laboratoryRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Laboratory updateLaboratory(Laboratory body) {
        try {
            LaboratoryValidator.validate(body);
            return laboratoryRepository.findById(body.getId())
                    .map( laboratory -> {
                        laboratory.setName(body.getName());
                        laboratory.setCode(body.getCode());
                        laboratory.setDescription(body.getDescription());
                        laboratory.setActive(body.isActive());
                        laboratory.setUpdatedAt(LocalDateTime.now());
                        return laboratoryRepository.save(laboratory);
                    })
                    .orElseThrow(() -> new NotFoundException("Laboratory id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteLaboratory(String id) {
        try {
            Laboratory laboratory = findLaboratoryById(id);
            laboratory.setDeletedAt(LocalDateTime.now());
            laboratoryRepository.save(laboratory);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
