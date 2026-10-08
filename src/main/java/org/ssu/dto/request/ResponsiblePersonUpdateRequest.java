package org.ssu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ResponsiblePersonUpdateRequest", description = "Payload to fully replace a responsible person")
public class ResponsiblePersonUpdateRequest {

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Last name", example = "Ivanov", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "First name", example = "Ivan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank
    @Size(max = 150)
    @Schema(description = "Position", example = "Warehouse Manager", requiredMode = Schema.RequiredMode.REQUIRED)
    private String position;

    @Pattern(regexp = "^[+0-9()\\-\\s]{5,20}$", message = "Invalid phone format")
    @Schema(description = "Phone number", example = "+7-900-123-45-67")
    private String phone;
}
