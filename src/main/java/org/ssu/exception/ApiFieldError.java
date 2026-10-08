package org.ssu.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ApiFieldError", description = "Validation error for a single field")
public class ApiFieldError {

    @Schema(description = "Field name", example = "cost")
    private String field;

    @Schema(description = "Validation message", example = "must be greater than or equal to 0")
    private String message;
}
