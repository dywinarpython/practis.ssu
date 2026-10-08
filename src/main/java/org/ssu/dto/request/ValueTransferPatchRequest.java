package org.ssu.dto.request;

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
@Schema(name = "ValueTransferPatchRequest", description = "Payload to partially update a value transfer; at least one field is required")
public class ValueTransferPatchRequest {

    @Schema(description = "Sender responsible person id", example = "1")
    private Integer fromResponsiblePersonId;

    @Schema(description = "Recipient responsible person id", example = "3")
    private Integer toResponsiblePersonId;

    @Schema(description = "Material value id", example = "2")
    private Integer materialValueId;

    @Schema(description = "Transfer date and time", example = "2026-09-24T10:00:00")
    private LocalDateTime date;

    @AssertTrue(message = "At least one field must be provided")
    @Schema(hidden = true)
    public boolean isAnyFieldProvided() {
        return fromResponsiblePersonId != null || toResponsiblePersonId != null
                || materialValueId != null || date != null;
    }

    @AssertTrue(message = "fromResponsiblePersonId and toResponsiblePersonId must not be the same")
    @Schema(hidden = true)
    public boolean isFromAndToDifferent() {
        return fromResponsiblePersonId == null || !fromResponsiblePersonId.equals(toResponsiblePersonId);
    }
}
