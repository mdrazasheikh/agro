package com.test.agro.controller;

import com.test.agro.contracts.ReportInterface;
import com.test.agro.dto.FieldProcessedReportDto;
import com.test.agro.hawks.ResponseHawk;
import com.test.agro.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/report")
public class ReportController {

    ReportInterface reportInterface;

    public ReportController(ReportService reportService) {
        this.reportInterface = reportService;
    }

    @GetMapping("/processed-field")
    public ResponseEntity<?> processedFieldReport(@RequestParam(name = "field_name", required = false) String fieldName,
                                                  @RequestParam(name = "culture", required = false) String culture,
                                                  @RequestParam(name = "date", required = false) @Size(min = 10, max = 10) String date,
                                                  @RequestParam(name = "tractor_name", required = false) String tractorName) {

        FieldProcessedReportDto fieldProcessedReportDto = reportInterface.getProcessedField(fieldName, tractorName, date, culture);

        return ResponseHawk.sendOk(fieldProcessedReportDto);
    }
}
