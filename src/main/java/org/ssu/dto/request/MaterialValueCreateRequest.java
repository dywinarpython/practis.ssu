package org.ssu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "MaterialValueCreateRequest", description = "Payload to create a material value")
public class MaterialValueCreateRequest {

    @NotBlank
    @Schema(description = "Name", example = "Lenovo ThinkPad", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Schema(description = "Category", example = "Equipment", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @NotNull
    @DecimalMin(value = "0.0")
    @Schema(description = "Cost", example = "120000.00", minimum = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal cost;

    @NotBlank
    @Schema(description = "Condition", example = "NEW", requiredMode = Schema.RequiredMode.REQUIRED)
    private String condition;

    @Schema(description = "Responsible person id (optional)", example = "1")
    private Integer responsiblePersonId;
}
