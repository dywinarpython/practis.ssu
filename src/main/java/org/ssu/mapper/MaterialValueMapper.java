package org.ssu.mapper;

import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.projection.MaterialValueProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialValueMapper {

    MaterialValueResponse toResponse(MaterialValueProjection projection);
}
