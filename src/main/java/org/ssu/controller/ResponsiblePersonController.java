package org.ssu.controller;

import org.ssu.annotation.ApiV1Controller;
import org.ssu.dto.request.ResponsiblePersonCreateRequest;
import org.ssu.dto.request.ResponsiblePersonPatchRequest;
import org.ssu.dto.request.ResponsiblePersonUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.ResponsiblePersonResponse;
import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.exception.ApiErrorResponse;
import org.ssu.service.ResponsiblePersonService;
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
@Tag(name = "Responsible Persons", description = "Operations on responsible persons")
public class ResponsiblePersonController {

    private final ResponsiblePersonService responsiblePersonService;

    @GetMapping("/responsible-persons")
    @Operation(operationId = "getResponsiblePersons", summary = "Get all responsible persons")
    @ApiResponse(responseCode = "200", description = "List of responsible persons",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsiblePersonResponse.class)))
    public ResponseEntity<List<ResponsiblePersonResponse>> getResponsiblePersons() {
        return ResponseEntity.ok(responsiblePersonService.getAll());
    }

    @GetMapping("/responsible-persons/{id}")
    @Operation(operationId = "getResponsiblePersonById", summary = "Get a responsible person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsible person found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsiblePersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ResponsiblePersonResponse> getResponsiblePersonById(
            @Parameter(description = "Responsible person id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(responsiblePersonService.getById(id));
    }

    @PostMapping("/responsible-persons")
    @Operation(operationId = "createResponsiblePerson", summary = "Create a new responsible person")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Responsible person created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsiblePersonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ResponsiblePersonResponse> createResponsiblePerson(
            @Valid @RequestBody ResponsiblePersonCreateRequest request) {
        ResponsiblePersonResponse response = responsiblePersonService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/responsible-persons/" + response.getId())).body(response);
    }

    @PutMapping("/responsible-persons/{id}")
    @Operation(operationId = "updateResponsiblePerson", summary = "Fully replace a responsible person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsible person updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsiblePersonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ResponsiblePersonResponse> updateResponsiblePerson(
            @Parameter(description = "Responsible person id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody ResponsiblePersonUpdateRequest request) {
        return ResponseEntity.ok(responsiblePersonService.update(id, request));
    }

    @PatchMapping("/responsible-persons/{id}")
    @Operation(operationId = "patchResponsiblePerson", summary = "Partially update a responsible person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsible person updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsiblePersonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or empty patch body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ResponsiblePersonResponse> patchResponsiblePerson(
            @Parameter(description = "Responsible person id", example = "1", required = true) @PathVariable Integer id,
            @Valid @RequestBody ResponsiblePersonPatchRequest request) {
        return ResponseEntity.ok(responsiblePersonService.patch(id, request));
    }

    @DeleteMapping("/responsible-persons/{id}")
    @Operation(operationId = "deleteResponsiblePerson", summary = "Delete a responsible person")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Responsible person deleted"),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Responsible person has related records",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteResponsiblePerson(
            @Parameter(description = "Responsible person id", example = "1", required = true) @PathVariable Integer id) {
        responsiblePersonService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/responsible-persons/{id}/material-values")
    @Operation(operationId = "getResponsiblePersonMaterialValues", summary = "Get material values assigned to a responsible person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of material values",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MaterialValueResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<List<MaterialValueResponse>> getResponsiblePersonMaterialValues(
            @Parameter(description = "Responsible person id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(responsiblePersonService.getMaterialValues(id));
    }

    @GetMapping("/responsible-persons/{id}/transfers/given")
    @Operation(operationId = "getResponsiblePersonTransfersGiven", summary = "Get transfers given by a responsible person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of value transfers",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValueTransferResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<List<ValueTransferResponse>> getResponsiblePersonTransfersGiven(
            @Parameter(description = "Responsible person id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(responsiblePersonService.getTransfersGiven(id));
    }

    @GetMapping("/responsible-persons/{id}/transfers/received")
    @Operation(operationId = "getResponsiblePersonTransfersReceived", summary = "Get transfers received by a responsible person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of value transfers",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValueTransferResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsible person not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<List<ValueTransferResponse>> getResponsiblePersonTransfersReceived(
            @Parameter(description = "Responsible person id", example = "1", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(responsiblePersonService.getTransfersReceived(id));
    }
}
