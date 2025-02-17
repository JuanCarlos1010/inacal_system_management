package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.InternalVersionDto;
import com.inacal.system.management.entity.InternalVersion;
import com.inacal.system.management.mapper.InternalVersionMapper;
import com.inacal.system.management.service.InternalVersionService;

@RestController
@RequestMapping(path = "/internal-versions")
public class InternalVersionController {
    private final InternalVersionService internalVersionService;
    private final InternalVersionMapper internalVersionMapper;

    public InternalVersionController(InternalVersionService internalVersionService,
                                     InternalVersionMapper internalVersionMapper) {
        this.internalVersionService = internalVersionService;
        this.internalVersionMapper = internalVersionMapper;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<InternalVersionDto>> findAllRegistrationDates(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<InternalVersion> pageResponse = internalVersionService.findAllRegistrationDates(pagination);
        List<InternalVersionDto> dtoList = internalVersionMapper.toDTO(pageResponse.getResult());
        PageResponse<InternalVersionDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InternalVersionDto> findRegistrationDateById(@PathVariable String id) {
        InternalVersionDto result = internalVersionMapper.toDTO(internalVersionService.findRegistrationDateById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<InternalVersionDto> saveRegistrationDate(@RequestBody InternalVersionDto body) {
        InternalVersionDto result = internalVersionMapper.toDTO(
                internalVersionService.saveRegistrationDate(internalVersionMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<InternalVersionDto> updateRegistrationDate(@RequestBody InternalVersionDto body) {
        InternalVersionDto result = internalVersionMapper.toDTO(
                internalVersionService.updateRegistrationDate(internalVersionMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteRegistrationDate(@PathVariable String id) {
        boolean result = internalVersionService.deleteRegistrationDate(id);
        return ResponseEntity.ok(result);
    }
}