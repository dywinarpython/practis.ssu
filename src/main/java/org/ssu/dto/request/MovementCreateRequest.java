package org.ssu.dto.request;

import org.ssu.enums.MovementStatus;
import org.ssu.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "MovementCreateRequest", description = "Payload to create a movement")
public class MovementCreateRequest {

    @NotNull
    @Schema(description = "Warehouse id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer warehouseId;

    @NotNull
    @Schema(description = "Movement type", example = "INCOMING", requiredMode = Schema.RequiredMode.REQUIRED)
    private MovementType movementType;

    @NotNull
    @Schema(description = "Movement status", example = "IN_PROGRESS", requiredMode = Schema.RequiredMode.REQUIRED)
    private MovementStatus status;

    @NotNull
    @Schema(description = "Movement date and time", example = "2026-09-23T16:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime date;

    @NotNull
    @Schema(description = "Material value id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer materialValueId;
}
