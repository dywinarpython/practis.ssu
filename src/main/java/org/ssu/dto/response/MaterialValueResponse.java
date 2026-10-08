package org.ssu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "MaterialValueResponse", description = "Material value")
public class MaterialValueResponse {

    @Schema(description = "Material value id", example = "1")
    private Integer id;

    @Schema(description = "Name", example = "Lenovo ThinkPad")
    private String name;

    @Schema(description = "Category", example = "Equipment")
    private String category;

    @Schema(description = "Cost", example = "120000.00")
    private BigDecimal cost;

    @Schema(description = "Condition", example = "NEW")
    private String condition;

    @Schema(description = "Responsible person id", example = "1")
    private Integer responsiblePersonId;
}
