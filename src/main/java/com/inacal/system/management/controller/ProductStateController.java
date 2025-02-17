package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.ProductStateDto;
import com.inacal.system.management.entity.ProductState;
import com.inacal.system.management.mapper.ProductStateMapper;
import com.inacal.system.management.service.ProductStateService;

@RestController
@RequestMapping(path = "/product-states")
public class ProductStateController {
    private final ProductStateService productStateService;
    private final ProductStateMapper productStateMapper;

    public ProductStateController(ProductStateService productStateService, ProductStateMapper productStateMapper) {
        this.productStateService = productStateService;
        this.productStateMapper = productStateMapper;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<ProductStateDto>> findAllProductStates(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<ProductState> pageResponse = productStateService.findAllProductStates(pagination);
        List<ProductStateDto> dtoList = productStateMapper.toDTO(pageResponse.getResult());
        PageResponse<ProductStateDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductStateDto> findProductStateById(@PathVariable String id) {
        ProductStateDto result = productStateMapper.toDTO(productStateService.findProductStateById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<ProductStateDto> saveProductState(@RequestBody ProductStateDto body) {
        ProductStateDto result = productStateMapper.toDTO(
                productStateService.saveProductState(productStateMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<ProductStateDto> updateProductState(@RequestBody ProductStateDto body) {
        ProductStateDto result = productStateMapper.toDTO(
                productStateService.updateProductState(productStateMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteProductState(@PathVariable String id) {
        boolean result = productStateService.deleteProductState(id);
        return ResponseEntity.ok(result);
    }
}