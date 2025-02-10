package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.ProductGroupDto;
import com.inacal.system.management.entity.ProductGroup;
import com.inacal.system.management.mapper.ProductGroupMapper;
import com.inacal.system.management.service.ProductGroupService;

@RestController
@RequestMapping(path = "/product-groups")
public class ProductGroupController {
    private final ProductGroupService productGroupService;
    private final ProductGroupMapper productGroupMapper;

    public ProductGroupController(ProductGroupMapper productGroupMapper, ProductGroupService productGroupService) {
        this.productGroupMapper = productGroupMapper;
        this.productGroupService = productGroupService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<ProductGroupDto>> findAllProductGroups(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<ProductGroup> pageResponse = productGroupService.findAllProductGroups(pagination);
        List<ProductGroupDto> dtoList = productGroupMapper.toDTOList(pageResponse.getResult());
        PageResponse<ProductGroupDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductGroupDto> findProductGroupById(@PathVariable String id) {
        ProductGroupDto result = productGroupMapper.toDTO(productGroupService.findProductGroupById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<ProductGroupDto> saveProductGroup(@RequestBody ProductGroupDto body) {
        ProductGroupDto result = productGroupMapper.toDTO(productGroupService.saveProductGroup(productGroupMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<ProductGroupDto> updateProductGroup(@RequestBody ProductGroupDto body) {
        ProductGroupDto result = productGroupMapper.toDTO(productGroupService.updateProductGroup(productGroupMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteProductGroup(@PathVariable String id) {
        boolean result = productGroupService.deleteProductGroup(id);
        return ResponseEntity.ok(result);
    }
}