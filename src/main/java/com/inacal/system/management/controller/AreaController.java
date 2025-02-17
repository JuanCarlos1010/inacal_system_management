package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.dto.AreaDto;
import com.inacal.system.management.entity.Area;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.mapper.AreaMapper;
import com.inacal.system.management.service.AreaService;

@RestController
@RequestMapping(path = "/areas")
public class AreaController {
    private final AreaService areaService;
    private final AreaMapper areaMapper;

    public AreaController(AreaService areaService, AreaMapper areaMapper) {
        this.areaService = areaService;
        this.areaMapper = areaMapper;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<AreaDto>> findAllAreas(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<Area> pageResponse = areaService.findAllAreas(pagination);
        List<AreaDto> dtoList = areaMapper.toDTO(pageResponse.getResult());
        PageResponse<AreaDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AreaDto> findAreaById(@PathVariable String id) {
        AreaDto result = areaMapper.toDTO(areaService.findAreaById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<AreaDto> saveArea(@RequestBody AreaDto body) {
        AreaDto result = areaMapper.toDTO(areaService.saveArea(areaMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<AreaDto> updateArea(@RequestBody AreaDto body) {
        AreaDto result = areaMapper.toDTO(areaService.updateArea(areaMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteArea(@PathVariable String id) {
        boolean result = areaService.deleteArea(id);
        return ResponseEntity.ok(result);
    }
}