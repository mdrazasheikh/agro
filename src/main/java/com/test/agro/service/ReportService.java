package com.test.agro.service;

import com.test.agro.contracts.ReportInterface;
import com.test.agro.dto.FieldProcessedReportDto;
import com.test.agro.entity.AreaAggregatorEntity;
import com.test.agro.entity.FieldProcessingEntity;
import com.test.agro.repository.FieldProcessingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService implements ReportInterface {

    FieldProcessingRepository fieldProcessingRepository;

    public ReportService(FieldProcessingRepository fieldProcessingRepository) {
        this.fieldProcessingRepository = fieldProcessingRepository;
    }

    @Override
    public FieldProcessedReportDto getProcessedField(String fieldName, String tractorName, String date, String culture) {

        List<FieldProcessingEntity> fieldProcessingEntities = fieldProcessingRepository.filter(fieldName, tractorName, date, culture);
        AreaAggregatorEntity areaAggregatorEntity = fieldProcessingRepository.aggregateProcessedArea(fieldName, tractorName, date, culture);

        return new FieldProcessedReportDto(fieldProcessingEntities, areaAggregatorEntity);
    }
}
