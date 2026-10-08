package org.ssu.service.impl;

import org.ssu.dto.request.MaterialValueCreateRequest;
import org.ssu.dto.request.MaterialValuePatchRequest;
import org.ssu.dto.request.MaterialValueUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.MovementResponse;
import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.enums.EntityStatus;
import org.ssu.enums.MovementStatus;
import org.ssu.enums.MovementType;
import org.ssu.exception.ResourceNotFoundException;
import org.ssu.mapper.MaterialValueMapper;
import org.ssu.mapper.MovementMapper;
import org.ssu.mapper.ValueTransferMapper;
import org.ssu.projection.MaterialValueProjection;
import org.ssu.repository.*;
import org.ssu.service.MaterialValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialValueServiceImpl implements MaterialValueService {

    private final MaterialValueRepository materialValueRepository;
    private final ResponsiblePersonRepository responsiblePersonRepository;
    private final MovementRepository movementRepository;
    private final WarehouseRepository warehouseRepository;
    private final ValueTransferRepository valueTransferRepository;
    private final MaterialValueMapper materialValueMapper;
    private final MovementMapper movementMapper;
    private final ValueTransferMapper valueTransferMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MaterialValueResponse> getAll() {
        return materialValueRepository.findAllMaterialValues().stream()
                .map(materialValueMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialValueResponse getById(Integer id) {
        MaterialValueProjection projection = materialValueRepository.findMaterialValueById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material value with id " + id + " not found"));
        return materialValueMapper.toResponse(projection);
    }

    @Override
    @Transactional
    public MaterialValueResponse create(MaterialValueCreateRequest request) {
        validateResponsiblePersonExists(request.getResponsiblePersonId());
        validateResponsibleWarehouseExists(request.getWarehouseId());

        Integer id = materialValueRepository.insertMaterialValue(
                request.getName(), request.getCategory(), request.getCost(), EntityStatus.ACTIVE.toString(),
                request.getCondition(), request.getResponsiblePersonId());

        movementRepository.insertMovement(request.getWarehouseId(),
                MovementType.INCOMING.toString(), MovementStatus.COMPLETED.toString(), LocalDateTime.now(), id);
        valueTransferRepository.insertValueTransfer(null, request.getResponsiblePersonId(), id, LocalDateTime.now());
        return getById(id);
    }

    @Override
    @Transactional
    public MaterialValueResponse update(Integer id, MaterialValueUpdateRequest request) {
        if (!materialValueRepository.existsMaterialValue(id)) {
            throw new ResourceNotFoundException("Material value with id " + id + " not found");
        }
        materialValueRepository.updateMaterialValue(
                id, request.getName(), request.getCategory(), request.getCost(),
                request.getCondition());
        return getById(id);
    }

    @Override
    @Transactional
    public MaterialValueResponse patch(Integer id, MaterialValuePatchRequest request) {
        MaterialValueProjection existing = materialValueRepository.findMaterialValueById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material value with id " + id + " not found"));

        String name = request.getName() != null ? request.getName() : existing.getName();
        String category = request.getCategory() != null ? request.getCategory() : existing.getCategory();
        BigDecimal cost = request.getCost() != null ? request.getCost() : existing.getCost();
        String condition = request.getCondition() != null ? request.getCondition() : existing.getCondition();

        materialValueRepository.updateMaterialValue(id, name, category, cost, condition);
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!materialValueRepository.existsMaterialValue(id)) {
            throw new ResourceNotFoundException("Material value with id " + id + " not found");
        }
        materialValueRepository.deleteMaterialValue(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementResponse> getMovements(Integer id) {
        if (!materialValueRepository.existsMaterialValue(id)) {
            throw new ResourceNotFoundException("Material value with id " + id + " not found");
        }
        return materialValueRepository.findMovementsByMaterialValueId(id).stream()
                .map(movementMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueTransferResponse> getTransfers(Integer id) {
        if (!materialValueRepository.existsMaterialValue(id)) {
            throw new ResourceNotFoundException("Material value with id " + id + " not found");
        }
        return materialValueRepository.findTransfersByMaterialValueId(id).stream()
                .map(valueTransferMapper::toResponse)
                .toList();
    }

    private void validateResponsiblePersonExists(Integer responsiblePersonId) {
        if (responsiblePersonId != null && !responsiblePersonRepository.existsResponsiblePerson(responsiblePersonId)) {
            throw new ResourceNotFoundException("Responsible person with id " + responsiblePersonId + " not found");
        }
    }

    private void validateResponsibleWarehouseExists(Integer warehouseId) {
        if (warehouseId != null && !warehouseRepository.existsWarehouse(warehouseId)) {
            throw new ResourceNotFoundException("Warehouse with id " + warehouseId + " not found");
        }
    }
}
