package org.ssu.dto.request;

import org.ssu.enums.MovementStatus;
import org.ssu.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MovementPatchRequest", description = "Payload to partially update a movement; at least one field is required")
public class MovementPatchRequest {

    @Schema(description = "Warehouse id", example = "2")
    private Integer warehouseId;

    @Schema(description = "Movement type", example = "OUTGOING")
    private MovementType movementType;

    @Schema(description = "Movement status", example = "COMPLETED")
    private MovementStatus status;

    @Schema(description = "Movement date and time", example = "2026-09-24T09:00:00")
    private LocalDateTime date;

    @Schema(description = "Material value id", example = "3")
    private Integer materialValueId;

    @AssertTrue(message = "At least one field must be provided")
    @Schema(hidden = true)
    public boolean isAnyFieldProvided() {
        return warehouseId != null || movementType != null || status != null || date != null || materialValueId != null;
    }
}
