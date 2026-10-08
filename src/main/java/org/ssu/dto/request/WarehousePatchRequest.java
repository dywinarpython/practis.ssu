package org.ssu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
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
@Schema(name = "WarehousePatchRequest", description = "Payload to partially update a warehouse; at least one field is required")
public class WarehousePatchRequest {

    @Size(max = 255)
    @Schema(description = "Warehouse name", example = "North Warehouse")
    private String name;

    @Size(max = 500)
    @Schema(description = "Warehouse address", example = "Rostov-on-Don, Severnaya 5")
    private String address;

    @AssertTrue(message = "At least one field must be provided")
    @Schema(hidden = true)
    public boolean isAnyFieldProvided() {
        return name != null || address != null;
    }
}
