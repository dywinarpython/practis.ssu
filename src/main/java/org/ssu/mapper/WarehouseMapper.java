package org.ssu.mapper;

import org.ssu.dto.response.WarehouseResponse;
import org.ssu.projection.WarehouseProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseResponse toResponse(WarehouseProjection projection);
}
