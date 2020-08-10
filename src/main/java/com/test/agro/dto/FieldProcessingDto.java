package com.test.agro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldProcessingDto {
    @JsonProperty("field_id")
    @NotEmpty(message = "fieldId cannot be empty")
    String fieldId;
    @JsonProperty("tractor_id")
    @NotEmpty(message = "tractor_id cannot be empty")
    String tractorId;
    @Size(min = 10, max = 10, message = "date should be in timestamp format of length 10")
    @NotEmpty(message = "date cannot be empty")
    String date;
    @NotNull(message = "area cannot be empty")
    Double area;
}
