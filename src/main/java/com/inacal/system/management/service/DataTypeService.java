package com.inacal.system.management.service;

import java.util.Optional;
import com.inacal.management.model.Pagination;
import org.springframework.stereotype.Service;
import com.inacal.management.model.PageResponse;
import com.inacal.system.management.entity.DataType;
import com.inacal.management.exception.NotFoundException;
import com.inacal.management.exception.BadRequestException;
import com.inacal.management.exception.InternalServerException;
import com.inacal.system.management.repository.DataTypeRepository;

@Service
public class DataTypeService {
    private final DataTypeRepository dataTypeRepository;

    public DataTypeService(DataTypeRepository dataTypeRepository) {
        this.dataTypeRepository = dataTypeRepository;
    }

    public PageResponse<DataType> findAllDataTypes(Pagination pagination) {
        try {
            return dataTypeRepository.findAll(pagination);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public DataType findDataTypeById(String id) {
        try {
            return dataTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("DataType not found"));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public DataType saveDataType(DataType body) {
        try {
            body.setId(null);
            Optional<DataType> existingDataType = dataTypeRepository.findByName(body.getName());
            if (existingDataType.isPresent()) {
                throw new BadRequestException("DataType name already exists");
            }
            return dataTypeRepository.save(body);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }
}
