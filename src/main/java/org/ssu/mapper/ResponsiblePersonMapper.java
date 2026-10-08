package org.ssu.mapper;

import org.ssu.dto.response.ResponsiblePersonResponse;
import org.ssu.projection.ResponsiblePersonProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponsiblePersonMapper {

    ResponsiblePersonResponse toResponse(ResponsiblePersonProjection projection);
}
