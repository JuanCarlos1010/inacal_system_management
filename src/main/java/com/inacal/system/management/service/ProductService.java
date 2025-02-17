package com.inacal.system.management.service;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.entity.Product;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.system.management.validator.ProductValidator;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public PageResponse<Product> findAllProducts(Pagination pagination) {
        try {
            return productRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Product findProductById(String id) {
        try {
            return productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Product saveProduct(Product body) {
        try {
            body.setId(null);
            ProductValidator.validate(body);
            Optional<Product> existingProduct = productRepository.findByName(body.getName());
            if (existingProduct.isPresent()) {
                throw new BadRequestException("Product name already exists");
            }
            body.setCreatedAt(LocalDateTime.now());
            return productRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public Product updateProduct(Product body) {
        try {
            return productRepository.findById(body.getId())
                    .map( product -> {
                        product.setName(body.getName());
                        product.setSystemName(body.getSystemName());
                        product.setUpdatedAt(LocalDateTime.now());
                        return productRepository.save(product);
                    })
                    .orElseThrow(() -> new NotFoundException("Product id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteProduct(String id) {
        try {
            Product product = findProductById(id);
            product.setDeletedAt(LocalDateTime.now());
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
