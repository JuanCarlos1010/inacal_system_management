package com.inacal.system.management.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.entity.Area;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.system.management.repository.AreaRepository;

@Service
public class AreaService {
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public PageResponse<Area> findAllAreas(Pagination pagination) {
        try {
            return areaRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Area findAreaById(String id) {
        try {
            return areaRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Area not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Area saveArea(Area body) {
        try {
            body.setId(null);
            Optional<Area> existingArea = areaRepository.findByName(body.getName());
            if (existingArea.isPresent()) {
                    throw new BadRequestException("Area name already exists");
            }
            body.setCreatedAt(DateTimeHelper.now());
            return areaRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Area updateArea(Area body) {
        try {
            body.setId(null);
            return areaRepository.findById(body.getId())
                    .map( area -> {
                        area.setName(body.getName());
                        area.setUpdatedAt(DateTimeHelper.now());
                        return areaRepository.save(body);
                    })
                    .orElseThrow(() -> new NotFoundException("Area id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteArea(String id) {
        try {
            Area area = findAreaById(id);
            area.setDeletedAt(DateTimeHelper.now());
            areaRepository.save(area);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
