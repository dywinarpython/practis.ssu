package org.ssu.mapper;

import org.ssu.dto.response.MovementResponse;
import org.ssu.projection.MovementProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    MovementResponse toResponse(MovementProjection projection);
}
