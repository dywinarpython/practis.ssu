package org.ssu.controller;

import org.ssu.annotation.ApiV1Controller;
import org.ssu.dto.request.MovementCreateRequest;
import org.ssu.dto.response.MovementResponse;
import org.ssu.exception.ApiErrorResponse;
import org.ssu.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;

@ApiV1Controller
@RequiredArgsConstructor
@Tag(name = "Movements", description = "Operations on material value movements")
public class MovementController {

    private final MovementService movementService;

    @GetMapping("/movements")
    @Operation(operationId = "getMovements", summary = "Get all movements")
    @ApiResponse(responseCode = "200", description = "List of movements",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovementResponse.class)))
    public ResponseEntity<List<MovementResponse>> getMovements() {
        return ResponseEntity.ok(movementService.getAll());
    }

    @GetMapping("/movements/{id}")
    @Operation(operationId = "getMovementById", summary = "Get a movement by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movement found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovementResponse.class))),
            @ApiResponse(responseCode = "404", description = "Movement not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<MovementResponse> getMovementById(
            @Parameter(description = "Movement id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(movementService.getById(id));
    }

    @PostMapping("/movements")
    @Operation(operationId = "createMovement", summary = "Create a new movement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movement created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovementResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Warehouse or material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<MovementResponse> createMovement(@Valid @RequestBody MovementCreateRequest request) {
        MovementResponse response = movementService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/movements/" + response.getId())).body(response);
    }
}
