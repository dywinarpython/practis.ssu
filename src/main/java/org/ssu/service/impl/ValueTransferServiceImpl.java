package org.ssu.service.impl;

import org.ssu.dto.request.ValueTransferCreateRequest;
import org.ssu.dto.request.ValueTransferPatchRequest;
import org.ssu.dto.request.ValueTransferUpdateRequest;
import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.exception.BadRequestException;
import org.ssu.exception.ResourceNotFoundException;
import org.ssu.mapper.ValueTransferMapper;
import org.ssu.projection.ValueTransferProjection;
import org.ssu.repository.MaterialValueRepository;
import org.ssu.repository.ResponsiblePersonRepository;
import org.ssu.repository.ValueTransferRepository;
import org.ssu.service.ValueTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValueTransferServiceImpl implements ValueTransferService {

    private final ValueTransferRepository valueTransferRepository;
    private final ResponsiblePersonRepository responsiblePersonRepository;
    private final MaterialValueRepository materialValueRepository;
    private final ValueTransferMapper valueTransferMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ValueTransferResponse> getAll() {
        return valueTransferRepository.findAllValueTransfers().stream()
                .map(valueTransferMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ValueTransferResponse getById(Integer id) {
        ValueTransferProjection projection = valueTransferRepository.findValueTransferById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Value transfer with id " + id + " not found"));
        return valueTransferMapper.toResponse(projection);
    }

    @Override
    @Transactional
    public ValueTransferResponse create(ValueTransferCreateRequest request) {
        validateFromToDifferent(request.getFromResponsiblePersonId(), request.getToResponsiblePersonId());
        validateResponsiblePersonExists(request.getFromResponsiblePersonId());
        validateResponsiblePersonExists(request.getToResponsiblePersonId());
        validateMaterialValueExists(request.getMaterialValueId());
        Integer id = valueTransferRepository.insertValueTransfer(
                request.getFromResponsiblePersonId(), request.getToResponsiblePersonId(),
                request.getMaterialValueId(), request.getDate());
        return getById(id);
    }

    @Override
    @Transactional
    public ValueTransferResponse update(Integer id, ValueTransferUpdateRequest request) {
        if (!valueTransferRepository.existsValueTransfer(id)) {
            throw new ResourceNotFoundException("Value transfer with id " + id + " not found");
        }
        validateFromToDifferent(request.getFromResponsiblePersonId(), request.getToResponsiblePersonId());
        validateResponsiblePersonExists(request.getFromResponsiblePersonId());
        validateResponsiblePersonExists(request.getToResponsiblePersonId());
        validateMaterialValueExists(request.getMaterialValueId());

        valueTransferRepository.updateValueTransfer(
                id, request.getFromResponsiblePersonId(), request.getToResponsiblePersonId(),
                request.getMaterialValueId(), request.getDate());
        return getById(id);
    }

    @Override
    @Transactional
    public ValueTransferResponse patch(Integer id, ValueTransferPatchRequest request) {
        ValueTransferProjection existing = valueTransferRepository.findValueTransferById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Value transfer with id " + id + " not found"));

        Integer fromId = request.getFromResponsiblePersonId() != null
                ? request.getFromResponsiblePersonId() : existing.getFromResponsiblePersonId();
        Integer toId = request.getToResponsiblePersonId() != null
                ? request.getToResponsiblePersonId() : existing.getToResponsiblePersonId();
        Integer materialValueId = request.getMaterialValueId() != null
                ? request.getMaterialValueId() : existing.getMaterialValueId();
        LocalDateTime date = request.getDate() != null ? request.getDate() : existing.getDate();

        validateResponsiblePersonExists(fromId);
        validateResponsiblePersonExists(toId);
        validateMaterialValueExists(materialValueId);
        validateFromToDifferent(fromId, toId);

        valueTransferRepository.updateValueTransfer(id, fromId, toId, materialValueId, date);
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!valueTransferRepository.existsValueTransfer(id)) {
            throw new ResourceNotFoundException("Value transfer with id " + id + " not found");
        }
        valueTransferRepository.deleteValueTransfer(id);
    }

    private void validateResponsiblePersonExists(Integer responsiblePersonId) {
        if (responsiblePersonId != null && !responsiblePersonRepository.existsResponsiblePerson(responsiblePersonId)) {
            throw new ResourceNotFoundException("Responsible person with id " + responsiblePersonId + " not found");
        }
    }

    private void validateMaterialValueExists(Integer materialValueId) {
        if (!materialValueRepository.existsMaterialValue(materialValueId)) {
            throw new ResourceNotFoundException("Material value with id " + materialValueId + " not found");
        }
    }

    private void validateFromToDifferent(Integer fromId, Integer toId) {
        if (fromId != null && fromId.equals(toId)) {
            throw new BadRequestException("fromResponsiblePersonId and toResponsiblePersonId must not be the same");
        }
    }
}
