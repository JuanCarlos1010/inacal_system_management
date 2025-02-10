package com.inacal.system.management.service;

import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.model.PageResponse;
import com.inacal.management.model.Pagination;
import com.inacal.management.time.DateTimeHelper;
import com.inacal.system.management.entity.BusinessData;
import com.inacal.system.management.entity.DataType;
import com.inacal.system.management.repository.BusinessDataRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BusinessDataService {
    private final BusinessDataRepository businessDataRepository;

    public BusinessDataService(BusinessDataRepository businessDataRepository) {
        this.businessDataRepository = businessDataRepository;
    }

    PageResponse<BusinessData> findAllBusinessData(Pagination pagination) {
        try {
            return businessDataRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public BusinessData findBusinessDataById(String id) {
        try {
            return businessDataRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("BusinessData not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public BusinessData saveBusinessData(BusinessData body) {
        try {
            body.setId(null);
            DataType dataType = body.getDataType();
            if (dataType == null) {
                throw new BadRequestException("DataType is required");
            } else {
                Optional<BusinessData> existingBusinessData = businessDataRepository.findByName(body.getName());
                if (existingBusinessData.isPresent()) {
                    throw new BadRequestException("BusinessData name already exists");
                }
            }
            body.setDataType(dataType);
            body.setCreatedAt(DateTimeHelper.now());
            return businessDataRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public BusinessData updateBusinessData(BusinessData body) {
        try {
            body.setId(null);
            return businessDataRepository.findById(body.getId())
                    .map( businessData -> {
                        businessData.setName(body.getName());
                        businessData.setSystemName(body.getSystemName());
                        businessData.setUpdatedAt(DateTimeHelper.now());
                        return businessDataRepository.save(body);
                    })
                    .orElseThrow(() -> new NotFoundException("BusinessData id does not exist"));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteBusinessData(String id) {
        try {
            BusinessData businessData = findBusinessDataById(id);
            businessData.setDeletedAt(DateTimeHelper.now());
            businessDataRepository.save(businessData);
            return true;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
