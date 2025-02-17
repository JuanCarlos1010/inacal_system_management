package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.DataTypeDto;
import com.inacal.system.management.entity.DataType;
import com.inacal.system.management.mapper.DataTypeMapper;
import com.inacal.system.management.service.DataTypeService;

@RestController
@RequestMapping(path = "/data-types")
public class DataTypeController {
    private final DataTypeService dataTypeService;
    private final DataTypeMapper dataTypeMapper;

    public DataTypeController(DataTypeService dataTypeService, DataTypeMapper dataTypeMapper) {
        this.dataTypeService = dataTypeService;
        this.dataTypeMapper = dataTypeMapper;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<DataTypeDto>> findAllDataTypes(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<DataType> pageResponse = dataTypeService.findAllDataTypes(pagination);
        List<DataTypeDto> dtoList = dataTypeMapper.toDTO(pageResponse.getResult());
        PageResponse<DataTypeDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataTypeDto> findDataTypeById(@PathVariable String id) {
        DataTypeDto result = dataTypeMapper.toDTO(dataTypeService.findDataTypeById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<DataTypeDto> saveDataType(@RequestBody DataTypeDto body) {
        DataTypeDto result = dataTypeMapper.toDTO(
                dataTypeService.saveDataType(dataTypeMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}