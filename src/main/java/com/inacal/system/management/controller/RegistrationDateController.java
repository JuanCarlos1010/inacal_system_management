package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.RegistrationDateDto;
import com.inacal.system.management.entity.RegistrationDate;
import com.inacal.system.management.mapper.RegistrationDateMapper;
import com.inacal.system.management.service.RegistrationDateService;

@RestController
@RequestMapping(path = "/registration-dates")
public class RegistrationDateController {
    private final RegistrationDateService registrationDateService;
    private final RegistrationDateMapper registrationDateMapper;

    public RegistrationDateController(RegistrationDateMapper registrationDateMapper, RegistrationDateService registrationDateService) {
        this.registrationDateMapper = registrationDateMapper;
        this.registrationDateService = registrationDateService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<RegistrationDateDto>> findAllRegistrationDates(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<RegistrationDate> pageResponse = registrationDateService.findAllRegistrationDates(pagination);
        List<RegistrationDateDto> dtoList = registrationDateMapper.toDTOList(pageResponse.getResult());
        PageResponse<RegistrationDateDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RegistrationDateDto> findRegistrationDateById(@PathVariable String id) {
        RegistrationDateDto result = registrationDateMapper.toDTO(registrationDateService.findRegistrationDateById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<RegistrationDateDto> saveRegistrationDate(@RequestBody RegistrationDateDto body) {
        RegistrationDateDto result = registrationDateMapper.toDTO(registrationDateService.saveRegistrationDate(registrationDateMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<RegistrationDateDto> updateRegistrationDate(@RequestBody RegistrationDateDto body) {
        RegistrationDateDto result = registrationDateMapper.toDTO(registrationDateService.updateRegistrationDate(registrationDateMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteRegistrationDate(@PathVariable String id) {
        boolean result = registrationDateService.deleteRegistrationDate(id);
        return ResponseEntity.ok(result);
    }
}