package com.inacal.system.management.service;

import java.util.Optional;
import com.inacal.management.model.Pagination;
import org.springframework.stereotype.Service;
import com.inacal.management.model.PageResponse;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.management.exception.NotFoundException;
import com.inacal.system.management.entity.FormatVersion;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
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
            Optional<FormatVersion> existingFormatVersion = formatVersionRepository.findByTitle(body.getTitle());
            if (existingFormatVersion.isPresent()) {
                throw new BadRequestException("FormatVersion name already exists");
            }
            body.setCreatedAt(DateTimeHelper.now());
            return formatVersionRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public FormatVersion updateFormatVersion(FormatVersion body) {
        try {
            body.setId(null);
            return formatVersionRepository.findById(body.getId())
                    .map( formatVersion -> {
                        formatVersion.setTitle(body.getTitle());
                        formatVersion.setCode(body.getCode());
                        formatVersion.setVersion(body.getVersion());
                        formatVersion.setUpdatedAt(DateTimeHelper.now());
                        return formatVersionRepository.save(body);
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
            formatVersion.setDeletedAt(DateTimeHelper.now());
            formatVersionRepository.save(formatVersion);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
