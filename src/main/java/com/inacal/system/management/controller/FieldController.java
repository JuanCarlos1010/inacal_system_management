package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.FieldDto;
import com.inacal.system.management.entity.Field;
import com.inacal.system.management.mapper.FieldMapper;
import com.inacal.system.management.service.FieldService;

@RestController
@RequestMapping(path = "/fields")
public class FieldController {
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FieldController(FieldMapper fieldMapper, FieldService fieldService) {
        this.fieldMapper = fieldMapper;
        this.fieldService = fieldService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<FieldDto>> findAllFields(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<Field> pageResponse = fieldService.findAllFields(pagination);
        List<FieldDto> dtoList = fieldMapper.toDTOList(pageResponse.getResult());
        PageResponse<FieldDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FieldDto> findFieldById(@PathVariable String id) {
        FieldDto result = fieldMapper.toDTO(fieldService.findFieldById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<FieldDto> saveField(@RequestBody FieldDto body) {
        FieldDto result = fieldMapper.toDTO(fieldService.saveField(fieldMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<FieldDto> updateField(@RequestBody FieldDto body) {
        FieldDto result = fieldMapper.toDTO(fieldService.updateField(fieldMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteField(@PathVariable String id) {
        boolean result = fieldService.deleteField(id);
        return ResponseEntity.ok(result);
    }
}