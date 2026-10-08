package org.ssu.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ResponsiblePersonResponse", description = "Responsible person")
public class ResponsiblePersonResponse {

    @Schema(description = "Responsible person id", example = "1")
    private Integer id;

    @Schema(description = "Last name", example = "Ivanov")
    private String lastName;

    @Schema(description = "First name", example = "Ivan")
    private String firstName;

    @Schema(description = "Position", example = "Warehouse Manager")
    private String position;

    @Schema(description = "Phone number", example = "+7-900-123-45-67")
    private String phone;
}
