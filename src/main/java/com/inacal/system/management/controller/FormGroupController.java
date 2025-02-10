package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.FormGroupDto;
import com.inacal.system.management.entity.FormGroup;
import com.inacal.system.management.mapper.FormGroupMapper;
import com.inacal.system.management.service.FormGroupService;

@RestController
@RequestMapping(path = "/form-groups")
public class FormGroupController {
    private final FormGroupService formGroupService;
    private final FormGroupMapper formGroupMapper;

    public FormGroupController(FormGroupMapper formGroupMapper, FormGroupService formGroupService) {
        this.formGroupMapper = formGroupMapper;
        this.formGroupService = formGroupService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<FormGroupDto>> findAllFormGroups(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<FormGroup> pageResponse = formGroupService.findAllFormGroups(pagination);
        List<FormGroupDto> dtoList = formGroupMapper.toDTOList(pageResponse.getResult());
        PageResponse<FormGroupDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FormGroupDto> findFormGroupById(@PathVariable String id) {
        FormGroupDto result = formGroupMapper.toDTO(formGroupService.findFormGroupById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<FormGroupDto> saveFormGroup(@RequestBody FormGroupDto body) {
        FormGroupDto result = formGroupMapper.toDTO(formGroupService.saveFormGroup(formGroupMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<FormGroupDto> updateFormGroup(@RequestBody FormGroupDto body) {
        FormGroupDto result = formGroupMapper.toDTO(formGroupService.updateFormGroup(formGroupMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteFormGroup(@PathVariable String id) {
        boolean result = formGroupService.deleteFormGroup(id);
        return ResponseEntity.ok(result);
    }
}