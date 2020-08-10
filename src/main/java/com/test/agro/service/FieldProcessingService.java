package com.test.agro.service;

import com.test.agro.contracts.FieldProcessingInterface;
import com.test.agro.dto.FieldProcessingDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.entity.AreaAggregatorEntity;
import com.test.agro.entity.FieldEntity;
import com.test.agro.entity.FieldProcessingEntity;
import com.test.agro.entity.TractorEntity;
import com.test.agro.exception.ActionNotAllowedException;
import com.test.agro.exception.NotFoundException;
import com.test.agro.exception.FieldProcessException;
import com.test.agro.exception.ResourceAccessNotAllowedException;
import com.test.agro.repository.FieldProcessingRepository;
import com.test.agro.repository.FieldRepository;
import com.test.agro.repository.TractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldProcessingService implements FieldProcessingInterface {

    FieldProcessingRepository fieldProcessingRepository;
    FieldRepository fieldRepository;
    TractorRepository tractorRepository;

    @Autowired
    public FieldProcessingService(FieldProcessingRepository fieldProcessingRepository,
                                  FieldRepository fieldRepository,
                                  TractorRepository tractorRepository) {
        this.fieldProcessingRepository = fieldProcessingRepository;
        this.fieldRepository = fieldRepository;
        this.tractorRepository = tractorRepository;
    }

    @Override
    public FieldProcessingEntity create(FieldProcessingDto fieldProcessingDto, TokenClaimDto claimDto)
            throws NotFoundException, ResourceAccessNotAllowedException, FieldProcessException, ResourceAccessNotAllowedException {
        FieldEntity fieldEntity;
        try {
            fieldEntity = fieldRepository.get(fieldProcessingDto.getFieldId());
        } catch (Exception e) {
            throw new NotFoundException("Invalid field_id");
        }
        if (!claimDto.getRole().equals("admin") && !fieldEntity.getCreatedBy().equals(claimDto.getUserId())) {
            throw new ResourceAccessNotAllowedException("field_id cannot be assigned by the user");
        }
        if (fieldProcessingDto.getArea() > fieldEntity.getArea()) {
            throw new FieldProcessException("area cannot be greater than field area");
        }
        TractorEntity tractorEntity;
        try {
            tractorEntity = tractorRepository.get(fieldProcessingDto.getTractorId());
        } catch (Exception e) {
            throw new NotFoundException("Invalid tractor_id");
        }
        if (!claimDto.getRole().equals("admin") && !tractorEntity.getCreatedBy().equals(claimDto.getUserId())) {
            throw new ResourceAccessNotAllowedException("tractor_id cannot be assigned by the user");
        }
        AreaAggregatorEntity areaAggregatorEntity = fieldProcessingRepository.getTotalAreaByFieldIdDateIsProcessed(fieldProcessingDto.getFieldId(), fieldProcessingDto.getDate());

        if (areaAggregatorEntity != null) {
            Double totalArea = areaAggregatorEntity.getTotalArea() + fieldProcessingDto.getArea();
            if (totalArea > fieldEntity.getArea()) {
                throw new FieldProcessException("cannot allocate this area for field for the day");
            }
        }

        if (fieldProcessingRepository.isTractorAllocatedForDate(fieldProcessingDto.getTractorId(), fieldProcessingDto.getDate())) {
            throw new FieldProcessException("cannot allocate this tractor for the day");
        }

        FieldProcessingEntity fieldProcessingEntity = new FieldProcessingEntity(fieldProcessingDto, fieldEntity, tractorEntity, claimDto);

        return fieldProcessingRepository.save(fieldProcessingEntity);
    }

    @Override
    public List<FieldProcessingEntity> getAll() {
        return fieldProcessingRepository.getAll();
    }

    @Override
    public FieldProcessingEntity get(String id) {
        return fieldProcessingRepository.get(id);
    }

    @Override
    public FieldProcessingEntity markIsProcessed(String id, TokenClaimDto claimDto) {
        FieldProcessingEntity fieldProcessingEntity = fieldProcessingRepository.get(id);
        if (fieldProcessingEntity.getIsProcessed()) {
            throw new ActionNotAllowedException("Field is already processed");
        }
        FieldEntity fieldEntity = fieldRepository.get(fieldProcessingEntity.getFieldId());

        AreaAggregatorEntity areaAggregatorEntity = fieldProcessingRepository.getTotalAreaByFieldIdDateIsProcessed(fieldProcessingEntity.getFieldId(), fieldProcessingEntity.getDate());

        if (areaAggregatorEntity != null) {
            Double totalArea = areaAggregatorEntity.getTotalArea() + fieldProcessingEntity.getArea();
            if (totalArea > fieldEntity.getArea()) {
                throw new FieldProcessException("cannot allocate this area for field for the day");
            }
        }

        if (fieldProcessingRepository.isTractorAllocatedForDate(fieldProcessingEntity.getTractorId(), fieldProcessingEntity.getDate())) {
            throw new FieldProcessException("cannot allocate this tractor for the day");
        }

        return fieldProcessingRepository.markProcessed(fieldProcessingEntity);
    }
}
