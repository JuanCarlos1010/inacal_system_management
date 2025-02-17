package com.inacal.system.management.service;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.inacal.management.model.Pagination;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.entity.ProductState;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.validator.ProductStateValidator;
import com.inacal.system.management.repository.ProductStateRepository;

@Service
public class ProductStateService {
    private final ProductStateRepository productStateRepository;

    public ProductStateService(ProductStateRepository productStateRepository) {
        this.productStateRepository = productStateRepository;
    }

    public PageResponse<ProductState> findAllProductStates(Pagination pagination) {
        try {
            return productStateRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductState findProductStateById(String id) {
        try {
            return productStateRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("ProductState not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductState saveProductState(ProductState body) {
        try {
            body.setId(null);
            ProductStateValidator.validate(body);
            Optional<ProductState> existingProductState = productStateRepository.findByName(body.getName());
            if (existingProductState.isPresent()) {
                throw new BadRequestException("ProductState name already exists");
            }
            body.setCreatedAt(LocalDateTime.now());
            return productStateRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductState updateProductState(ProductState body) {
        try {
            ProductStateValidator.validate(body);
            return productStateRepository.findById(body.getId())
                    .map( productState -> {
                        productState.setName(body.getName());
                        productState.setSystemName(body.getSystemName());
                        productState.setUpdatedAt(LocalDateTime.now());
                        return productStateRepository.save(productState);
                    })
                    .orElseThrow(() -> new NotFoundException("ProductState id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteProductState(String id) {
        try {
            ProductState productState = findProductStateById(id);
            productState.setDeletedAt(LocalDateTime.now());
            productStateRepository.save(productState);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
