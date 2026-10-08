package org.ssu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ssu.enums.EntityStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "WarehouseResponse", description = "Warehouse")
public class WarehouseResponse {

    @Schema(description = "Warehouse id", example = "1")
    private Integer id;

    @Schema(description = "Warehouse name", example = "Main Warehouse")
    private String name;

    @Schema(description = "Warehouse address", example = "Rostov-on-Don, Industrialnaya 10")
    private String address;

    @Schema(description = "Status", example = "ACTIVE")
    private EntityStatus status;
}
