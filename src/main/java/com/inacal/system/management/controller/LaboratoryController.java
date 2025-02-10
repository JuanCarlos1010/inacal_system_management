package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.LaboratoryDto;
import com.inacal.system.management.entity.Laboratory;
import com.inacal.system.management.mapper.LaboratoryMapper;
import com.inacal.system.management.service.LaboratoryService;

@RestController
@RequestMapping(path = "/laboratories")
public class LaboratoryController {
    private final LaboratoryService laboratoryService;
    private final LaboratoryMapper laboratoryMapper;

    public LaboratoryController(LaboratoryMapper laboratoryMapper, LaboratoryService laboratoryService) {
        this.laboratoryMapper = laboratoryMapper;
        this.laboratoryService = laboratoryService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<LaboratoryDto>> findAllLaboratories(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<Laboratory> pageResponse = laboratoryService.findAllLaboratories(pagination);
        List<LaboratoryDto> dtoList = laboratoryMapper.toDTOList(pageResponse.getResult());
        PageResponse<LaboratoryDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LaboratoryDto> findLaboratoryById(@PathVariable String id) {
        LaboratoryDto result = laboratoryMapper.toDTO(laboratoryService.findLaboratoryById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<LaboratoryDto> saveLaboratory(@RequestBody LaboratoryDto body) {
        LaboratoryDto result = laboratoryMapper.toDTO(laboratoryService.saveLaboratory(laboratoryMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<LaboratoryDto> updateLaboratory(@RequestBody LaboratoryDto body) {
        LaboratoryDto result = laboratoryMapper.toDTO(laboratoryService.updateLaboratory(laboratoryMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteLaboratory(@PathVariable String id) {
        boolean result = laboratoryService.deleteLaboratory(id);
        return ResponseEntity.ok(result);
    }
}