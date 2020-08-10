package com.test.agro.service;

import com.test.agro.contracts.FieldInterface;
import com.test.agro.dto.FieldDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.entity.FieldEntity;
import com.test.agro.exception.ResourceAccessNotAllowedException;
import com.test.agro.repository.FieldRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FieldService implements FieldInterface {

    FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public FieldEntity create(FieldDto fieldDto, TokenClaimDto claimDto) {
        FieldEntity fieldEntity = new FieldEntity(fieldDto, claimDto);

        return fieldRepository.save(fieldEntity);
    }

    @Override
    public List<FieldEntity> getAll() {
        return fieldRepository.getAll();
    }

    @Override
    public FieldEntity get(String id) {
        return fieldRepository.get(id);
    }

    @Override
    public FieldEntity update(String id, FieldDto fieldDto, TokenClaimDto claimDto) {
        FieldEntity fieldEntity = fieldRepository.get(id);
        if (!claimDto.getRole().equals("admin") && !fieldEntity.getCreatedBy().equals(claimDto.getUserId())) {
            throw new ResourceAccessNotAllowedException("No Permission to access this resource");
        }
        fieldEntity.setName(fieldDto.getName());
        fieldEntity.setType(fieldDto.getType());
        fieldEntity.setArea(fieldDto.getArea());
        fieldEntity.setModifiedBy(claimDto.getUserId());

        return fieldRepository.save(fieldEntity);
    }
}
