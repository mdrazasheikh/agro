package com.test.agro.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.agro.dto.FieldProcessingDto;
import com.test.agro.dto.TokenClaimDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "fieldProcessing")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldProcessingEntity {
    @Id
    String id;
    @Indexed
    String tractorId;
    @Indexed
    String tractorName;
    @Indexed
    String fieldId;
    @Indexed
    String fieldName;
    @Indexed
    String culture;
    Double area;
    String date;
    Boolean isProcessed;
    @Indexed
    String createdBy;
    String modifiedBy;

    public FieldProcessingEntity(FieldProcessingDto fieldProcessingDto, FieldEntity fieldEntity, TractorEntity tractorEntity, TokenClaimDto claimDto) {
        this.setFieldId(fieldEntity.getId());
        this.setFieldName(fieldEntity.getName());
        this.setTractorId(tractorEntity.getId());
        this.setTractorName(tractorEntity.getName());
        this.setCulture(fieldEntity.getType());
        this.setArea(fieldProcessingDto.getArea());
        this.setDate(fieldProcessingDto.getDate());
        this.setCreatedBy(claimDto.getUserId());
    }
}
