package com.inacal.system.management.service;

import java.util.Optional;
import java.time.LocalDateTime;
import com.inacal.management.model.Pagination;
import org.springframework.stereotype.Service;
import com.inacal.management.model.PageResponse;
import com.inacal.management.exception.NotFoundException;
import com.inacal.system.management.entity.FormatVersion;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.validator.FormatVersionValidator;
import com.inacal.system.management.repository.FormatVersionRepository;

@Service
public class FormatVersionService {
    private final FormatVersionRepository formatVersionRepository;

    public FormatVersionService(FormatVersionRepository formatVersionRepository) {
        this.formatVersionRepository = formatVersionRepository;
    }

    public PageResponse<FormatVersion> findAllFormatVersions(Pagination pagination) {
        try {
            return formatVersionRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormatVersion findFormatVersionById(String id) {
        try {
            return formatVersionRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("FormatVersion not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormatVersion saveFormatVersion(FormatVersion body) {
        try {
            body.setId(null);
            FormatVersionValidator.validate(body);
            Optional<FormatVersion> existingFormatVersion = formatVersionRepository.findByTitle(body.getTitle());
            if (existingFormatVersion.isPresent()) {
                throw new BadRequestException("FormatVersion title already exists");
            }
            body.setCreatedAt(LocalDateTime.now());
            return formatVersionRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormatVersion updateFormatVersion(FormatVersion body) {
        try {
            FormatVersionValidator.validate(body);
            return formatVersionRepository.findById(body.getId())
                    .map( formatVersion -> {
                        formatVersion.setTitle(body.getTitle());
                        formatVersion.setCode(body.getCode());
                        formatVersion.setVersion(body.getVersion());
                        formatVersion.setUpdatedAt(LocalDateTime.now());
                        return formatVersionRepository.save(formatVersion);
                    })
                    .orElseThrow(() -> new NotFoundException("FormatVersion id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteFormatVersion(String id) {
        try {
            FormatVersion formatVersion = findFormatVersionById(id);
            formatVersion.setDeletedAt(LocalDateTime.now());
            formatVersionRepository.save(formatVersion);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
