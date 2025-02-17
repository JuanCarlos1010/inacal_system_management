package com.inacal.system.management.service;

import java.util.Optional;
import java.time.LocalDateTime;
import com.inacal.management.model.Pagination;
import org.springframework.stereotype.Service;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.entity.FormGroup;
import com.inacal.system.management.entity.ProductGroup;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.validator.ProductGroupValidator;
import com.inacal.system.management.repository.ProductGroupRepository;

@Service
public class ProductGroupService {
    private final ProductGroupRepository productGroupRepository;

    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    public PageResponse<ProductGroup> findAllProductGroups(Pagination pagination) {
        try {
            return productGroupRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductGroup findProductGroupById(String id) {
        try {
            return productGroupRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("ProductGroup not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductGroup saveProductGroup(ProductGroup body) {
        try {
            body.setId(null);
            ProductGroupValidator.validate(body);
            FormGroup formGroup = body.getFormGroup();
            if (formGroup == null) {
                throw new BadRequestException("FormGroup is required");
            } else {
                Optional<ProductGroup> existingProductGroup = productGroupRepository.findByName(body.getName());
                if (existingProductGroup.isPresent()) {
                    throw new BadRequestException("ProductGroup name already exists");
                }
            }
            body.setFormGroup(formGroup);
            body.setCreatedAt(LocalDateTime.now());
            return productGroupRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductGroup updateProductGroup(ProductGroup body) {
        try {
            ProductGroupValidator.validate(body);
            return productGroupRepository.findById(body.getId())
                    .map( productGroup -> {
                        productGroup.setName(body.getName());
                        productGroup.setSystemName(body.getSystemName());
                        productGroup.setUpdatedAt(LocalDateTime.now());
                        return productGroupRepository.save(productGroup);
                    })
                    .orElseThrow(() -> new NotFoundException("ProductGroup id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteProductGroup(String id) {
        try {
            ProductGroup productGroup = findProductGroupById(id);
            productGroup.setDeletedAt(LocalDateTime.now());
            productGroupRepository.save(productGroup);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
