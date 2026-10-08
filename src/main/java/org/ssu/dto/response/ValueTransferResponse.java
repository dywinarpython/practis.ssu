package org.ssu.dto.response;

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
@Schema(name = "ValueTransferResponse", description = "Value transfer")
public class ValueTransferResponse {

    @Schema(description = "Transfer id", example = "1")
    private Integer id;

    @Schema(description = "Sender responsible person id", example = "1")
    private Integer fromResponsiblePersonId;

    @Schema(description = "Recipient responsible person id", example = "2")
    private Integer toResponsiblePersonId;

    @Schema(description = "Material value id", example = "1")
    private Integer materialValueId;

    @Schema(description = "Transfer date and time", example = "2026-09-23T17:00:00")
    private LocalDateTime date;
}
