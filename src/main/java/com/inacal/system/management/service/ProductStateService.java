package com.inacal.system.management.service;

import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.ProductState;
import com.inacal.system.management.repository.ProductStateRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductStateService {
    private final ProductStateRepository productStateRepository;

    public ProductStateService(ProductStateRepository productStateRepository) {
        this.productStateRepository = productStateRepository;
    }

    PageResponse<ProductState> findAllProductStates(Pagination pagination) {
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
            Optional<ProductState> existingProductState = productStateRepository.findByName(body.getName());
            if (existingProductState.isPresent()) {
                throw new BadRequestException("ProductState name already exists");
            }
            body.setCreatedAt(DateTimeHelper.now());
            return productStateRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public ProductState updateProductState(ProductState body) {
        try {
            body.setId(null);
            return productStateRepository.findById(body.getId())
                    .map( productState -> {
                        productState.setName(body.getName());
                        productState.setUpdatedAt(DateTimeHelper.now());
                        return productStateRepository.save(body);
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
            productState.setDeletedAt(DateTimeHelper.now());
            productStateRepository.save(productState);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
