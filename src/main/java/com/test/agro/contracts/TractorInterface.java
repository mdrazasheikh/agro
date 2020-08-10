package com.test.agro.contracts;

import com.test.agro.dto.TokenClaimDto;
import com.test.agro.dto.TractorDto;
import com.test.agro.entity.TractorEntity;

import java.util.List;

public interface TractorInterface {
    TractorEntity create(TractorDto tractorDto, TokenClaimDto claimDto);

    List<TractorEntity> getAll();

    TractorEntity get(String id);

    TractorEntity update(String id, TractorDto tractorDto, TokenClaimDto claimDto);
}
