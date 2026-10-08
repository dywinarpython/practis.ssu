package org.ssu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
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
@Schema(name = "ValueTransferUpdateRequest", description = "Payload to fully replace a value transfer")
public class ValueTransferUpdateRequest {

    @Schema(description = "Sender responsible person id (optional)", example = "1")
    private Integer fromResponsiblePersonId;

    @Schema(description = "Recipient responsible person id (optional)", example = "2")
    private Integer toResponsiblePersonId;

    @NotNull
    @Schema(description = "Material value id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer materialValueId;

    @NotNull
    @Schema(description = "Transfer date and time", example = "2026-09-23T17:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime date;

    @AssertTrue(message = "fromResponsiblePersonId and toResponsiblePersonId must not be the same")
    @Schema(hidden = true)
    public boolean isFromAndToDifferent() {
        return fromResponsiblePersonId == null || !fromResponsiblePersonId.equals(toResponsiblePersonId);
    }
}
