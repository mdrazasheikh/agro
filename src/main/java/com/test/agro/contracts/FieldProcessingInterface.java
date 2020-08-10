package com.test.agro.contracts;

import com.test.agro.dto.FieldProcessingDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.entity.FieldProcessingEntity;

import java.util.List;

public interface FieldProcessingInterface {
    FieldProcessingEntity create(FieldProcessingDto fieldProcessingDto, TokenClaimDto claimDto);

    List<FieldProcessingEntity> getAll();

    FieldProcessingEntity get(String id);

    FieldProcessingEntity markIsProcessed(String id, TokenClaimDto claimDto);
}
