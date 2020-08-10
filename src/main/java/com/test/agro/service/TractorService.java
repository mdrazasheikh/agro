package com.test.agro.service;

import com.test.agro.contracts.TractorInterface;
import com.test.agro.dto.TokenClaimDto;
import com.test.agro.dto.TractorDto;
import com.test.agro.entity.TractorEntity;
import com.test.agro.exception.ResourceAccessNotAllowedException;
import com.test.agro.repository.TractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TractorService implements TractorInterface {

    TractorRepository tractorRepository;

    @Autowired
    public TractorService(TractorRepository tractorRepository) {
        this.tractorRepository = tractorRepository;
    }

    @Override
    public TractorEntity create(TractorDto tractorDto, TokenClaimDto claimDto) {
        TractorEntity tractorEntity = new TractorEntity(tractorDto, claimDto);
        return tractorRepository.save(tractorEntity);
    }

    @Override
    public List<TractorEntity> getAll() {
        return tractorRepository.getAll();
    }

    @Override
    public TractorEntity get(String id) {
        return tractorRepository.get(id);
    }

    @Override
    public TractorEntity update(String id, TractorDto tractorDto, TokenClaimDto claimDto) {
        TractorEntity tractorEntity = tractorRepository.get(id);
        if (!claimDto.getRole().equals("admin") && !tractorEntity.getCreatedBy().equals(claimDto.getUserId())) {
            throw new ResourceAccessNotAllowedException("No Permission to access this resource");
        }
        tractorEntity.setName(tractorDto.getName());
        tractorEntity.setModifiedBy(claimDto.getUserId());

        return tractorRepository.save(tractorEntity);
    }
}
