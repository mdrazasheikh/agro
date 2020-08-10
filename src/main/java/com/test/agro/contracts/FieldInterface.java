package com.test.agro.contracts;

import com.test.agro.dto.FieldDto;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.entity.FieldEntity;

import java.util.List;

public interface FieldInterface {
    FieldEntity create(FieldDto fieldDto, TokenClaimDto claimDto);

    List<FieldEntity> getAll();

    FieldEntity get(String id);

    FieldEntity update(String id, FieldDto fieldDto, TokenClaimDto claimDto);
}
