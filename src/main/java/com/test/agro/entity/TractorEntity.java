package com.test.agro.entity;

import com.test.agro.dto.TokenClaimDto;
import com.test.agro.dto.TractorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "tractors")
public class TractorEntity {
    @Id
    @Indexed(unique = true)
    String id;
    String name;
    String createdBy;
    String modifiedBy;

    public TractorEntity(TractorDto tractorDto, TokenClaimDto claimDto) {
        this.setName(tractorDto.getName());
        this.setCreatedBy(claimDto.getUserId());
    }
}
