package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.BusinessDataDto;
import com.inacal.system.management.entity.BusinessData;
import com.inacal.system.management.mapper.BusinessDataMapper;
import com.inacal.system.management.service.BusinessDataService;

@RestController
@RequestMapping(path = "/business-data")
public class BusinessDataController {
    private final BusinessDataService businessDataService;
    private final BusinessDataMapper businessDataMapper;

    public BusinessDataController(BusinessDataMapper businessDataMapper, BusinessDataService businessDataService) {
        this.businessDataMapper = businessDataMapper;
        this.businessDataService = businessDataService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<BusinessDataDto>> findAllBusinessData(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<BusinessData> pageResponse = businessDataService.findAllBusinessData(pagination);
        List<BusinessDataDto> dtoList = businessDataMapper.toDTOList(pageResponse.getResult());
        PageResponse<BusinessDataDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BusinessDataDto> findBusinessDataById(@PathVariable String id) {
        BusinessDataDto result = businessDataMapper.toDTO(businessDataService.findBusinessDataById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<BusinessDataDto> saveBusinessData(@RequestBody BusinessDataDto body) {
        BusinessDataDto result = businessDataMapper.toDTO(businessDataService.saveBusinessData(businessDataMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<BusinessDataDto> updateBusinessData(@RequestBody BusinessDataDto body) {
        BusinessDataDto result = businessDataMapper.toDTO(businessDataService.updateBusinessData(businessDataMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteBusinessData(@PathVariable String id) {
        boolean result = businessDataService.deleteBusinessData(id);
        return ResponseEntity.ok(result);
    }
}