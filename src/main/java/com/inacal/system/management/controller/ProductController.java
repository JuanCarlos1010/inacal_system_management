package com.inacal.system.management.controller;

import java.util.List;
import com.inacal.management.model.Env;
import org.springframework.http.HttpStatus;
import com.inacal.management.model.Pagination;
import org.springframework.http.ResponseEntity;
import com.inacal.management.model.PageResponse;
import org.springframework.web.bind.annotation.*;
import com.inacal.system.management.dto.ProductDto;
import com.inacal.system.management.entity.Product;
import com.inacal.system.management.mapper.ProductMapper;
import com.inacal.system.management.service.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductMapper productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @GetMapping(path = "")
    public ResponseEntity<PageResponse<ProductDto>> findAllProducts(
            @RequestParam(name = Env.PARAM_PAGE_NAME, defaultValue = Env.DEFAULT_PAGE_NUMB) int page,
            @RequestParam(name = Env.DEFAULT_PAGE_SIZE, defaultValue = Env.DEFAULT_PAGE_SIZE) int size) {
        Pagination pagination = new Pagination(page, size);
        PageResponse<Product> pageResponse = productService.findAllProducts(pagination);
        List<ProductDto> dtoList = productMapper.toDTOList(pageResponse.getResult());
        PageResponse<ProductDto> result = new PageResponse<>(pageResponse.getCount(), dtoList, pagination);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable String id) {
        ProductDto result = productMapper.toDTO(productService.findProductById(id));
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto body) {
        ProductDto result = productMapper.toDTO(productService.saveProduct(productMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto body) {
        ProductDto result = productMapper.toDTO(productService.updateProduct(productMapper.toEntity(body)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable String id) {
        boolean result = productService.deleteProduct(id);
        return ResponseEntity.ok(result);
    }
}