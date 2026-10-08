package org.ssu.service.impl;

import org.ssu.dto.request.WarehouseCreateRequest;
import org.ssu.dto.request.WarehousePatchRequest;
import org.ssu.dto.request.WarehouseUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.WarehouseResponse;
import org.ssu.enums.EntityStatus;
import org.ssu.exception.ConflictException;
import org.ssu.exception.ResourceNotFoundException;
import org.ssu.mapper.MaterialValueMapper;
import org.ssu.mapper.WarehouseMapper;
import org.ssu.projection.WarehouseProjection;
import org.ssu.repository.WarehouseRepository;
import org.ssu.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final MaterialValueMapper materialValueMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseResponse> getAll() {
        return warehouseRepository.findAllWarehouses().stream()
                .map(warehouseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseResponse getById(Integer id) {
        WarehouseProjection projection = warehouseRepository.findWarehouseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with id " + id + " not found"));
        return warehouseMapper.toResponse(projection);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialValueResponse> getMaterialValueByWarehouseId(Integer warehouseId) {
        return warehouseRepository.findMaterialValueById(warehouseId).stream().map(materialValueMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public WarehouseResponse create(WarehouseCreateRequest request) {
        Integer id = warehouseRepository.insertWarehouse(request.getName(), request.getAddress(), EntityStatus.ACTIVE.toString());
        return getById(id);
    }

    @Override
    @Transactional
    public WarehouseResponse update(Integer id, WarehouseUpdateRequest request) {
        if (!warehouseRepository.existsWarehouse(id)) {
            throw new ResourceNotFoundException("Warehouse with id " + id + " not found");
        }
        warehouseRepository.updateWarehouse(id, request.getName(), request.getAddress());
        return getById(id);
    }

    @Override
    @Transactional
    public WarehouseResponse patch(Integer id, WarehousePatchRequest request) {
        WarehouseProjection existing = warehouseRepository.findWarehouseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with id " + id + " not found"));

        String name = request.getName() != null ? request.getName() : existing.getName();
        String address = request.getAddress() != null ? request.getAddress() : existing.getAddress();

        warehouseRepository.updateWarehouse(id, name, address);
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!warehouseRepository.existsWarehouse(id)) {
            throw new ResourceNotFoundException("Warehouse with id " + id + " not found");
        }
        if (warehouseRepository.existsMovementsByWarehouseId(id)) {
            throw new ConflictException("Warehouse cannot be deleted because it has movements");
        }
        warehouseRepository.deleteWarehouse(id);
    }
}
