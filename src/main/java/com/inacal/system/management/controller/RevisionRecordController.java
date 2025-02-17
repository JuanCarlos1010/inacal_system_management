package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.RevisionRecordDto;
import com.inacal.system.management.entity.RevisionRecord;
import com.inacal.system.management.mapper.RevisionRecordMapper;
import com.inacal.system.management.service.RevisionRecordService;

@RestController
@RequestMapping(path = "/revision-records")
public class RevisionRecordController {
    private final RevisionRecordService revisionRecordService;
    private final RevisionRecordMapper revisionRecordMapper;

    public RevisionRecordController(RevisionRecordService revisionRecordService,
                                    RevisionRecordMapper revisionRecordMapper) {
        this.revisionRecordService = revisionRecordService;
        this.revisionRecordMapper = revisionRecordMapper;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<RevisionRecordDto>> findAllRevisionRecords(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<RevisionRecord> pageResponse = revisionRecordService.findAllRevisionRecords(pagination);
        List<RevisionRecordDto> dtoList = revisionRecordMapper.toDTO(pageResponse.getResult());
        PageResponse<RevisionRecordDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RevisionRecordDto> findRevisionRecordById(@PathVariable String id) {
        RevisionRecordDto result = revisionRecordMapper.toDTO(revisionRecordService.findRevisionRecordById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<RevisionRecordDto> saveRevisionRecord(@RequestBody RevisionRecordDto body) {
        RevisionRecordDto result = revisionRecordMapper.toDTO(
                revisionRecordService.saveRevisionRecord(revisionRecordMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<RevisionRecordDto> updateRevisionRecord(@RequestBody RevisionRecordDto body) {
        RevisionRecordDto result = revisionRecordMapper.toDTO(
                revisionRecordService.updateRevisionRecord(revisionRecordMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteRevisionRecord(@PathVariable String id) {
        boolean result = revisionRecordService.deleteRevisionRecord(id);
        return ResponseEntity.ok(result);
    }
}