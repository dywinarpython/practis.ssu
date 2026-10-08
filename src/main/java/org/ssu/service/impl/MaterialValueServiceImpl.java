package org.ssu.service.impl;

import org.ssu.dto.request.MaterialValueCreateRequest;
import org.ssu.dto.request.MaterialValuePatchRequest;
import org.ssu.dto.request.MaterialValueUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.MovementResponse;
import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.exception.ConflictException;
import org.ssu.exception.ResourceNotFoundException;
import org.ssu.mapper.MaterialValueMapper;
import org.ssu.mapper.MovementMapper;
import org.ssu.mapper.ValueTransferMapper;
import org.ssu.projection.MaterialValueProjection;
import org.ssu.repository.MaterialValueRepository;
import org.ssu.repository.ResponsiblePersonRepository;
import org.ssu.service.MaterialValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialValueServiceImpl implements MaterialValueService {

    private final MaterialValueRepository materialValueRepository;
    private final ResponsiblePersonRepository responsiblePersonRepository;
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
        Integer id = materialValueRepository.insertMaterialValue(
                request.getName(), request.getCategory(), request.getCost(),
                request.getCondition(), request.getResponsiblePersonId());
        return getById(id);
    }

    @Override
    @Transactional
    public MaterialValueResponse update(Integer id, MaterialValueUpdateRequest request) {
        if (!materialValueRepository.existsMaterialValue(id)) {
            throw new ResourceNotFoundException("Material value with id " + id + " not found");
        }
        validateResponsiblePersonExists(request.getResponsiblePersonId());
        materialValueRepository.updateMaterialValue(
                id, request.getName(), request.getCategory(), request.getCost(),
                request.getCondition(), request.getResponsiblePersonId());
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
        Integer responsiblePersonId = request.getResponsiblePersonId() != null
                ? request.getResponsiblePersonId() : existing.getResponsiblePersonId();

        validateResponsiblePersonExists(responsiblePersonId);
        materialValueRepository.updateMaterialValue(id, name, category, cost, condition, responsiblePersonId);
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!materialValueRepository.existsMaterialValue(id)) {
            throw new ResourceNotFoundException("Material value with id " + id + " not found");
        }
        if (materialValueRepository.existsMovementsByMaterialValueId(id)) {
            throw new ConflictException("Material value cannot be deleted because it has movements");
        }
        if (materialValueRepository.existsTransfersByMaterialValueId(id)) {
            throw new ConflictException("Material value cannot be deleted because it has transfers");
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
}
