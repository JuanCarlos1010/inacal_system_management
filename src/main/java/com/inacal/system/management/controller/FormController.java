package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.dto.FormDto;
import com.inacal.system.management.entity.Form;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.mapper.FormMapper;
import com.inacal.system.management.service.FormService;

@RestController
@RequestMapping(path = "/forms")
public class FormController {
    private final FormService formService;
    private final FormMapper formMapper;

    public FormController(FormService formService, FormMapper formMapper) {
        this.formService = formService;
        this.formMapper = formMapper;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<FormDto>> findAllForms(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<Form> pageResponse = formService.findAllForms(pagination);
        List<FormDto> dtoList = formMapper.toDTO(pageResponse.getResult());
        PageResponse<FormDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FormDto> findFormById(@PathVariable String id) {
        FormDto result = formMapper.toDTO(formService.findFormById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<FormDto> saveForm(@RequestBody FormDto body) {
        FormDto result = formMapper.toDTO(formService.saveForm(formMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<FormDto> updateForm(@RequestBody FormDto body) {
        FormDto result = formMapper.toDTO(formService.updateForm(formMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteForm(@PathVariable String id) {
        boolean result = formService.deleteForm(id);
        return ResponseEntity.ok(result);
    }
}