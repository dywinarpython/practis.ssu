package org.ssu.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ApiErrorResponse", description = "Standard error response")
public class ApiErrorResponse {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Time when the error occurred", example = "2026-09-23T16:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "HTTP status reason", example = "Not Found")
    private String error;

    @Schema(description = "Error message", example = "Material value with id 999 not found")
    private String message;

    @Schema(description = "Request path", example = "/api/v1/material-values/999")
    private String path;

    @Schema(description = "Field validation errors, present only for 400 responses")
    private List<ApiFieldError> fieldErrors;
}
