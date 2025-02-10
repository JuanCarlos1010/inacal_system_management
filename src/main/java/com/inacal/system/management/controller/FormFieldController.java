package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.FormFieldDto;
import com.inacal.system.management.entity.FormField;
import com.inacal.system.management.mapper.FormFieldMapper;
import com.inacal.system.management.service.FormFieldService;

@RestController
@RequestMapping(path = "/forms-field")
public class FormFieldController {
    private final FormFieldService formFieldService;
    private final FormFieldMapper formFieldMapper;

    public FormFieldController(FormFieldMapper formFieldMapper, FormFieldService formFieldService) {
        this.formFieldMapper = formFieldMapper;
        this.formFieldService = formFieldService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<FormFieldDto>> findAllFormFields(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<FormField> pageResponse = formFieldService.findAllFormsField(pagination);
        List<FormFieldDto> dtoList = formFieldMapper.toDTOList(pageResponse.getResult());
        PageResponse<FormFieldDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FormFieldDto> findFormFieldById(@PathVariable String id) {
        FormFieldDto result = formFieldMapper.toDTO(formFieldService.findFormFieldById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<FormFieldDto> saveFormField(@RequestBody FormFieldDto body) {
        FormFieldDto result = formFieldMapper.toDTO(formFieldService.saveFormField(formFieldMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteFormField(@PathVariable String id) {
        boolean result = formFieldService.deleteFormField(id);
        return ResponseEntity.ok(result);
    }
}