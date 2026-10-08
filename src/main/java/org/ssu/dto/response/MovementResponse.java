package org.ssu.dto.response;

import org.ssu.enums.MovementStatus;
import org.ssu.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "MovementResponse", description = "Movement")
public class MovementResponse {

    @Schema(description = "Movement id", example = "1")
    private Integer id;

    @Schema(description = "Warehouse id", example = "1")
    private Integer warehouseId;

    @Schema(description = "Movement type", example = "INCOMING")
    private MovementType movementType;

    @Schema(description = "Movement status", example = "IN_PROGRESS")
    private MovementStatus status;

    @Schema(description = "Movement date and time", example = "2026-09-23T16:30:00")
    private LocalDateTime date;

    @Schema(description = "Material value id", example = "1")
    private Integer materialValueId;
}
