package com.test.agro.dto;

import com.test.agro.validators.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    @NotEmpty(message = "name cannot be empty")
    String name;
    @NotEmpty(message = "type cannot be empty")
    @FieldType
    String type;
    @NotNull(message = "area cannot be empty")
    Double area;
}
