package org.ssu.controller;

import org.ssu.annotation.ApiV1Controller;
import org.ssu.dto.request.ValueTransferCreateRequest;
import org.ssu.dto.request.ValueTransferPatchRequest;
import org.ssu.dto.request.ValueTransferUpdateRequest;
import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.exception.ApiErrorResponse;
import org.ssu.service.ValueTransferService;
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
@Tag(name = "Value Transfers", description = "Operations on transfers of material values between responsible persons")
public class ValueTransferController {

    private final ValueTransferService valueTransferService;

    @GetMapping("/transfers")
    @Operation(operationId = "getValueTransfers", summary = "Get all value transfers")
    @ApiResponse(responseCode = "200", description = "List of value transfers",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValueTransferResponse.class)))
    public ResponseEntity<List<ValueTransferResponse>> getValueTransfers() {
        return ResponseEntity.ok(valueTransferService.getAll());
    }

    @GetMapping("/transfers/{id}")
    @Operation(operationId = "getValueTransferById", summary = "Get a value transfer by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Value transfer found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValueTransferResponse.class))),
            @ApiResponse(responseCode = "404", description = "Value transfer not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ValueTransferResponse> getValueTransferById(
            @Parameter(description = "Value transfer id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(valueTransferService.getById(id));
    }

    @PostMapping("/transfers")
    @Operation(operationId = "createValueTransfer", summary = "Create a new value transfer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Value transfer created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValueTransferResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed, including from/to equal",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person or material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ValueTransferResponse> createValueTransfer(
            @Valid @RequestBody ValueTransferCreateRequest request) {
        ValueTransferResponse response = valueTransferService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/transfers/" + response.getId())).body(response);
    }

    @PutMapping("/transfers/{id}")
    @Operation(operationId = "updateValueTransfer", summary = "Fully replace a value transfer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Value transfer updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValueTransferResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed, including from/to equal",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Value transfer, responsible person or material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ValueTransferResponse> updateValueTransfer(
            @Parameter(description = "Value transfer id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody ValueTransferUpdateRequest request) {
        return ResponseEntity.ok(valueTransferService.update(id, request));
    }

    @PatchMapping("/transfers/{id}")
    @Operation(operationId = "patchValueTransfer", summary = "Partially update a value transfer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Value transfer updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValueTransferResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed, empty patch body, or from/to equal",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Value transfer, responsible person or material value not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ValueTransferResponse> patchValueTransfer(
            @Parameter(description = "Value transfer id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody ValueTransferPatchRequest request) {
        return ResponseEntity.ok(valueTransferService.patch(id, request));
    }

    @DeleteMapping("/transfers/{id}")
    @Operation(operationId = "deleteValueTransfer", summary = "Delete a value transfer")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Value transfer deleted"),
            @ApiResponse(responseCode = "404", description = "Value transfer not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteValueTransfer(
            @Parameter(description = "Value transfer id", example = "1", required = true) @PathVariable Integer id) {
        valueTransferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
