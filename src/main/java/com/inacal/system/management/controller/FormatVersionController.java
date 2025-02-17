package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.FormatVersionDto;
import com.inacal.system.management.entity.FormatVersion;
import com.inacal.system.management.mapper.FormatVersionMapper;
import com.inacal.system.management.service.FormatVersionService;

@RestController
@RequestMapping(path = "/format-versions")
public class FormatVersionController {
    private final FormatVersionService formatVersionService;
    private final FormatVersionMapper formatVersionMapper;

    public FormatVersionController(FormatVersionService formatVersionService,
                                   FormatVersionMapper formatVersionMapper) {
        this.formatVersionService = formatVersionService;
        this.formatVersionMapper = formatVersionMapper;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<FormatVersionDto>> findAllFormatVersions(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<FormatVersion> pageResponse = formatVersionService.findAllFormatVersions(pagination);
        List<FormatVersionDto> dtoList = formatVersionMapper.toDTO(pageResponse.getResult());
        PageResponse<FormatVersionDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FormatVersionDto> findFormatVersionById(@PathVariable String id) {
        FormatVersionDto result = formatVersionMapper.toDTO(formatVersionService.findFormatVersionById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<FormatVersionDto> saveFormatVersion(@RequestBody FormatVersionDto body) {
        FormatVersionDto result = formatVersionMapper.toDTO(
                formatVersionService.saveFormatVersion(formatVersionMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<FormatVersionDto> updateFormatVersion(@RequestBody FormatVersionDto body) {
        FormatVersionDto result = formatVersionMapper.toDTO(
                formatVersionService.updateFormatVersion(formatVersionMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteFormatVersion(@PathVariable String id) {
        boolean result = formatVersionService.deleteFormatVersion(id);
        return ResponseEntity.ok(result);
    }
}