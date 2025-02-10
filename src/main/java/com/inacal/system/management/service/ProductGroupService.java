package com.inacal.system.management.service;

import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.FormGroup;
import com.inacal.system.management.entity.ProductGroup;
import com.inacal.system.management.repository.ProductGroupRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductGroupService {
    private final ProductGroupRepository productGroupRepository;

    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    PageResponse<ProductGroup> findAllProductGroups(Pagination pagination) {
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
            body.setCreatedAt(DateTimeHelper.now());
            return productGroupRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductGroup updateProductGroup(ProductGroup body) {
        try {
            body.setId(null);
            return productGroupRepository.findById(body.getId())
                    .map( productGroup -> {
                        productGroup.setName(body.getName());
                        productGroup.setSystemName(body.getSystemName());
                        productGroup.setUpdatedAt(DateTimeHelper.now());
                        return productGroupRepository.save(body);
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
            productGroup.setDeletedAt(DateTimeHelper.now());
            productGroupRepository.save(productGroup);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
