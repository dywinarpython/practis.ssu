package org.ssu.controller;

import org.ssu.annotation.ApiV1Controller;
import org.ssu.dto.request.WarehouseCreateRequest;
import org.ssu.dto.request.WarehousePatchRequest;
import org.ssu.dto.request.WarehouseUpdateRequest;
import org.ssu.dto.response.WarehouseResponse;
import org.ssu.exception.ApiErrorResponse;
import org.ssu.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;

@ApiV1Controller
@RequiredArgsConstructor
@Tag(name = "Warehouses", description = "Operations on warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    @Operation(operationId = "getWarehouses", summary = "Get all warehouses")
    @ApiResponse(responseCode = "200", description = "List of warehouses",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarehouseResponse.class)))
    public ResponseEntity<List<WarehouseResponse>> getWarehouses() {
        return ResponseEntity.ok(warehouseService.getAll());
    }

    @GetMapping("/warehouses/{id}")
    @Operation(operationId = "getWarehouseById", summary = "Get a warehouse by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Warehouse found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarehouseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Warehouse not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<WarehouseResponse> getWarehouseById(
            @Parameter(description = "Warehouse id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(warehouseService.getById(id));
    }

    @PostMapping("/warehouses")
    @Operation(operationId = "createWarehouse", summary = "Create a new warehouse")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Warehouse created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarehouseResponse.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Main Warehouse\",\"address\":\"Rostov-on-Don, Industrialnaya 10\"}"))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<WarehouseResponse> createWarehouse(@Valid @RequestBody WarehouseCreateRequest request) {
        WarehouseResponse response = warehouseService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/warehouses/" + response.getId())).body(response);
    }

    @PutMapping("/warehouses/{id}")
    @Operation(operationId = "updateWarehouse", summary = "Fully replace a warehouse")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Warehouse updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarehouseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Warehouse not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<WarehouseResponse> updateWarehouse(
            @Parameter(description = "Warehouse id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody WarehouseUpdateRequest request) {
        return ResponseEntity.ok(warehouseService.update(id, request));
    }

    @PatchMapping("/warehouses/{id}")
    @Operation(operationId = "patchWarehouse", summary = "Partially update a warehouse")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Warehouse updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WarehouseResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or empty patch body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Warehouse not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<WarehouseResponse> patchWarehouse(
            @Parameter(description = "Warehouse id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody WarehousePatchRequest request) {
        return ResponseEntity.ok(warehouseService.patch(id, request));
    }

    @DeleteMapping("/warehouses/{id}")
    @Operation(operationId = "deleteWarehouse", summary = "Delete a warehouse")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Warehouse deleted"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Warehouse has movements",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteWarehouse(
            @Parameter(description = "Warehouse id", example = "1", required = true) @PathVariable Integer id) {
        warehouseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
