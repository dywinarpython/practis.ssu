package org.ssu.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.ssu.annotation.ApiV1Controller;
import org.ssu.dto.request.MaterialValueCreateRequest;
import org.ssu.dto.request.MaterialValuePatchRequest;
import org.ssu.dto.request.MaterialValueUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.MovementResponse;
import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.exception.ApiErrorResponse;
import org.ssu.service.MaterialValueService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;

@ApiV1Controller
@RequiredArgsConstructor
@Tag(name = "Material Values", description = "Operations on material values")
public class MaterialValueController {

    private final MaterialValueService materialValueService;

    @GetMapping("/material-values")
    @Operation(operationId = "getMaterialValues", summary = "Get all material values")
    @ApiResponse(responseCode = "200", description = "List of material values",
            content = @Content(mediaType = "application/json", array = @ArraySchema(
                    schema = @Schema(implementation = MaterialValueResponse.class))))
    public ResponseEntity<List<MaterialValueResponse>> getMaterialValues() {
        return ResponseEntity.ok(materialValueService.getAll());
    }

    @GetMapping("/material-values/{id}")
    @Operation(operationId = "getMaterialValueById", summary = "Get a material value by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material value found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MaterialValueResponse.class))),
            @ApiResponse(responseCode = "404", description = "Material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<MaterialValueResponse> getMaterialValueById(
            @Parameter(description = "Material value id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(materialValueService.getById(id));
    }

    @PostMapping("/material-values")
    @Operation(operationId = "createMaterialValue", summary = "Create a new material value")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Material value created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MaterialValueResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<MaterialValueResponse> createMaterialValue(
            @Valid @RequestBody MaterialValueCreateRequest request) {
        MaterialValueResponse response = materialValueService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/material-values/" + response.getId())).body(response);
    }

    @PutMapping("/material-values/{id}")
    @Operation(operationId = "updateMaterialValue", summary = "Fully replace a material value")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material value updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MaterialValueResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Material value or responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<MaterialValueResponse> updateMaterialValue(
            @Parameter(description = "Material value id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody MaterialValueUpdateRequest request) {
        return ResponseEntity.ok(materialValueService.update(id, request));
    }

    @PatchMapping("/material-values/{id}")
    @Operation(operationId = "patchMaterialValue", summary = "Partially update a material value")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material value updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MaterialValueResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or empty patch body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Material value or responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<MaterialValueResponse> patchMaterialValue(
            @Parameter(description = "Material value id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody MaterialValuePatchRequest request) {
        return ResponseEntity.ok(materialValueService.patch(id, request));
    }

    @DeleteMapping("/material-values/{id}")
    @Operation(operationId = "deleteMaterialValue", summary = "Delete a material value")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Material value deleted"),
            @ApiResponse(responseCode = "404", description = "Material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Material value has related records",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteMaterialValue(
            @Parameter(description = "Material value id", example = "1", required = true) @PathVariable Integer id) {
        materialValueService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/material-values/{id}/movements")
    @Operation(operationId = "getMaterialValueMovements", summary = "Get movements for a material value")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of movements",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = MovementResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<List<MovementResponse>> getMaterialValueMovements(
            @Parameter(description = "Material value id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(materialValueService.getMovements(id));
    }

    @GetMapping("/material-values/{id}/transfers")
    @Operation(operationId = "getMaterialValueTransfers", summary = "Get transfers for a material value")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of value transfers",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ValueTransferResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<List<ValueTransferResponse>> getMaterialValueTransfers(
            @Parameter(description = "Material value id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(materialValueService.getTransfers(id));
    }
}
