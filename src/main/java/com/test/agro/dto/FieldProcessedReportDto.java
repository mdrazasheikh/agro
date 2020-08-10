package com.test.agro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.agro.entity.AreaAggregatorEntity;
import com.test.agro.entity.FieldProcessingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldProcessedReportDto {
    @JsonProperty("field_processed")
    List<FieldProcessingEntity> fieldProcessed;
    @JsonProperty("total_processed_area")
    Double totalProcessedArea;

    public FieldProcessedReportDto(List<FieldProcessingEntity> fieldProcessingEntities, AreaAggregatorEntity areaAggregatorEntity) {
        this.setFieldProcessed(fieldProcessingEntities);
        this.setTotalProcessedArea(areaAggregatorEntity.getTotalArea());
    }
}
