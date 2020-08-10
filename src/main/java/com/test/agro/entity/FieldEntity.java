package com.test.agro.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.agro.dto.FieldDto;
import com.test.agro.dto.TokenClaimDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "fields")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldEntity {
    @Id
    @Indexed(unique = true)
    String id;
    @Indexed
    String name;
    @Indexed
    String type;
    Double area;
    String createdBy;
    String modifiedBy;

    public FieldEntity(FieldDto fieldDto, TokenClaimDto claimDto) {
        this.setName(fieldDto.getName());
        this.setType(fieldDto.getType());
        this.setArea(fieldDto.getArea());
        this.setCreatedBy(claimDto.getUserId());
    }
}
