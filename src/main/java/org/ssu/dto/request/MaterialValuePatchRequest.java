package org.ssu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MaterialValuePatchRequest", description = "Payload to partially update a material value; at least one field is required")
public class MaterialValuePatchRequest {

    @Schema(description = "Name", example = "Lenovo ThinkPad")
    private String name;

    @Schema(description = "Category", example = "Equipment")
    private String category;

    @DecimalMin(value = "0.0")
    @Schema(description = "Cost", example = "95000.00", minimum = "0")
    private BigDecimal cost;

    @Schema(description = "Condition", example = "USED")
    private String condition;

    @AssertTrue(message = "At least one field must be provided")
    @Schema(hidden = true)
    public boolean isAnyFieldProvided() {
        return name != null || category != null || cost != null || condition != null;
    }
}
