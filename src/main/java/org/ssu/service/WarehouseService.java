package org.ssu.service;

import org.ssu.dto.request.WarehouseCreateRequest;
import org.ssu.dto.request.WarehousePatchRequest;
import org.ssu.dto.request.WarehouseUpdateRequest;
import org.ssu.dto.response.WarehouseResponse;

import java.util.List;

public interface WarehouseService {

    List<WarehouseResponse> getAll();

    WarehouseResponse getById(Integer id);

    WarehouseResponse create(WarehouseCreateRequest request);

    WarehouseResponse update(Integer id, WarehouseUpdateRequest request);

    WarehouseResponse patch(Integer id, WarehousePatchRequest request);

    void delete(Integer id);
}
