package com.test.agro.contracts;

import com.mongodb.lang.Nullable;
import com.test.agro.dto.FieldProcessedReportDto;

public interface ReportInterface {
    FieldProcessedReportDto getProcessedField(@Nullable String fieldName,
                                              @Nullable String tractorName,
                                              @Nullable String date,
                                              @Nullable String culture);
}
