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
        validateResponsiblePersonExists(request.getFromResponsiblePersonId());
        validateResponsiblePersonExists(request.getToResponsiblePersonId());
        validateMaterialValueExists(request.getMaterialValueId(), request.getFromResponsiblePersonId());
        Integer id = valueTransferRepository.insertValueTransfer(
                request.getFromResponsiblePersonId(), request.getToResponsiblePersonId(),
                request.getMaterialValueId(), request.getDate() == null ? LocalDateTime.now() : request.getDate());
        return getById(id);
    }

    private void validateResponsiblePersonExists(Integer responsiblePersonId) {
        if (responsiblePersonId != null && !responsiblePersonRepository.existsResponsiblePerson(responsiblePersonId)) {
            throw new ResourceNotFoundException("Responsible person with id " + responsiblePersonId + " not found");
        }
    }

    private void validateMaterialValueExists(Integer materialValueId, Integer responsiblePersonId) {
        if (!materialValueRepository.existsMaterialValueAndResponsiblePersonId(materialValueId, responsiblePersonId)) {
            if(materialValueRepository.existsMaterialValue(materialValueId)) {
                throw new ResourceNotFoundException("ResponsiblePerson with id: " + responsiblePersonId + " not owner material value with id " + materialValueId);
            }
            throw new ResourceNotFoundException("Material value with id " + materialValueId + " not found");
        }
    }
}
