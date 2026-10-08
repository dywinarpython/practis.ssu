package org.ssu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(name = "WarehouseUpdateRequest", description = "Payload to fully replace a warehouse")
public class WarehouseUpdateRequest {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Warehouse name", example = "Main Warehouse", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "Warehouse address", example = "Rostov-on-Don, Industrialnaya 10", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;
}
