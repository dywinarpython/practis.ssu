package org.ssu.service.impl;

import org.ssu.dto.request.MovementCreateRequest;
import org.ssu.dto.request.MovementPatchRequest;
import org.ssu.dto.request.MovementUpdateRequest;
import org.ssu.dto.response.MovementResponse;
import org.ssu.enums.MovementStatus;
import org.ssu.enums.MovementType;
import org.ssu.exception.ResourceNotFoundException;
import org.ssu.mapper.MovementMapper;
import org.ssu.projection.MovementProjection;
import org.ssu.repository.MaterialValueRepository;
import org.ssu.repository.MovementRepository;
import org.ssu.repository.WarehouseRepository;
import org.ssu.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final WarehouseRepository warehouseRepository;
    private final MaterialValueRepository materialValueRepository;
    private final MovementMapper movementMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovementResponse> getAll() {
        return movementRepository.findAllMovements().stream()
                .map(movementMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovementResponse getById(Integer id) {
        MovementProjection projection = movementRepository.findMovementById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement with id " + id + " not found"));
        return movementMapper.toResponse(projection);
    }

    @Override
    @Transactional
    public MovementResponse create(MovementCreateRequest request) {
        validateWarehouseExists(request.getWarehouseId());
        validateMaterialValueExists(request.getMaterialValueId());

        Integer id = movementRepository.insertMovement(
                request.getWarehouseId(),
                request.getMovementType().name(),
                request.getStatus().name(),
                request.getDate(),
                request.getMaterialValueId());
        return getById(id);
    }

    @Override
    @Transactional
    public MovementResponse update(Integer id, MovementUpdateRequest request) {
        if (!movementRepository.existsMovement(id)) {
            throw new ResourceNotFoundException("Movement with id " + id + " not found");
        }
        validateWarehouseExists(request.getWarehouseId());
        validateMaterialValueExists(request.getMaterialValueId());

        movementRepository.updateMovement(
                id, request.getWarehouseId(), request.getMovementType().name(),
                request.getStatus().name(), request.getDate(), request.getMaterialValueId());
        return getById(id);
    }

    @Override
    @Transactional
    public MovementResponse patch(Integer id, MovementPatchRequest request) {
        MovementProjection existing = movementRepository.findMovementById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement with id " + id + " not found"));

        Integer warehouseId = request.getWarehouseId() != null ? request.getWarehouseId() : existing.getWarehouseId();
        MovementType movementType = request.getMovementType() != null
                ? request.getMovementType() : MovementType.valueOf(existing.getMovementType());
        MovementStatus status = request.getStatus() != null
                ? request.getStatus() : MovementStatus.valueOf(existing.getStatus());
        LocalDateTime date = request.getDate() != null ? request.getDate() : existing.getDate();
        Integer materialValueId = request.getMaterialValueId() != null
                ? request.getMaterialValueId() : existing.getMaterialValueId();

        validateWarehouseExists(warehouseId);
        validateMaterialValueExists(materialValueId);

        movementRepository.updateMovement(id, warehouseId, movementType.name(), status.name(), date, materialValueId);
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!movementRepository.existsMovement(id)) {
            throw new ResourceNotFoundException("Movement with id " + id + " not found");
        }
        movementRepository.deleteMovement(id);
    }

    private void validateWarehouseExists(Integer warehouseId) {
        if (!warehouseRepository.existsWarehouse(warehouseId)) {
            throw new ResourceNotFoundException("Warehouse with id " + warehouseId + " not found");
        }
    }

    private void validateMaterialValueExists(Integer materialValueId) {
        if (!materialValueRepository.existsMaterialValue(materialValueId)) {
            throw new ResourceNotFoundException("Material value with id " + materialValueId + " not found");
        }
    }
}
