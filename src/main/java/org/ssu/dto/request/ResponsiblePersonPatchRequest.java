package org.ssu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
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
@Schema(name = "ResponsiblePersonPatchRequest", description = "Payload to partially update a responsible person; at least one field is required")
public class ResponsiblePersonPatchRequest {

    @Size(max = 100)
    @Schema(description = "Last name", example = "Ivanov")
    private String lastName;

    @Size(max = 100)
    @Schema(description = "First name", example = "Ivan")
    private String firstName;

    @Size(max = 150)
    @Schema(description = "Position", example = "Senior Warehouse Manager")
    private String position;

    @Pattern(regexp = "^[+0-9()\\-\\s]{5,20}$", message = "Invalid phone format")
    @Schema(description = "Phone number", example = "+7-900-123-45-67")
    private String phone;

    @AssertTrue(message = "At least one field must be provided")
    @Schema(hidden = true)
    public boolean isAnyFieldProvided() {
        return lastName != null || firstName != null || position != null || phone != null;
    }
}
