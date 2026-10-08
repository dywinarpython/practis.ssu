package org.ssu.mapper;

import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.projection.ValueTransferProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ValueTransferMapper {

    ValueTransferResponse toResponse(ValueTransferProjection projection);
}
