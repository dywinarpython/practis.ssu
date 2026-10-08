package org.ssu.service.impl;

import org.ssu.dto.request.ResponsiblePersonCreateRequest;
import org.ssu.dto.request.ResponsiblePersonPatchRequest;
import org.ssu.dto.request.ResponsiblePersonUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.ResponsiblePersonResponse;
import org.ssu.dto.response.ValueTransferResponse;
import org.ssu.enums.EntityStatus;
import org.ssu.exception.ConflictException;
import org.ssu.exception.ResourceNotFoundException;
import org.ssu.mapper.MaterialValueMapper;
import org.ssu.mapper.ResponsiblePersonMapper;
import org.ssu.mapper.ValueTransferMapper;
import org.ssu.projection.ResponsiblePersonProjection;
import org.ssu.repository.ResponsiblePersonRepository;
import org.ssu.service.ResponsiblePersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsiblePersonServiceImpl implements ResponsiblePersonService {

    private final ResponsiblePersonRepository responsiblePersonRepository;
    private final ResponsiblePersonMapper responsiblePersonMapper;
    private final MaterialValueMapper materialValueMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ResponsiblePersonResponse> getAll() {
        return responsiblePersonRepository.findAllResponsiblePersons().stream()
                .map(responsiblePersonMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsiblePersonResponse getById(Integer id) {
        ResponsiblePersonProjection projection = responsiblePersonRepository.findResponsiblePersonById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsible person with id " + id + " not found"));
        return responsiblePersonMapper.toResponse(projection);
    }

    @Override
    @Transactional
    public ResponsiblePersonResponse create(ResponsiblePersonCreateRequest request) {
        Integer id = responsiblePersonRepository.insertResponsiblePerson(
                request.getLastName(), request.getFirstName(), request.getPosition(), request.getPhone(), EntityStatus.ACTIVE.toString());
        return getById(id);
    }

    @Override
    @Transactional
    public ResponsiblePersonResponse update(Integer id, ResponsiblePersonUpdateRequest request) {
        if (!responsiblePersonRepository.existsResponsiblePerson(id)) {
            throw new ResourceNotFoundException("Responsible person with id " + id + " not found");
        }
        responsiblePersonRepository.updateResponsiblePerson(
                id, request.getLastName(), request.getFirstName(), request.getPosition(), request.getPhone());
        return getById(id);
    }

    @Override
    @Transactional
    public ResponsiblePersonResponse patch(Integer id, ResponsiblePersonPatchRequest request) {
        ResponsiblePersonProjection existing = responsiblePersonRepository.findResponsiblePersonById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsible person with id " + id + " not found"));

        String lastName = request.getLastName() != null ? request.getLastName() : existing.getLastName();
        String firstName = request.getFirstName() != null ? request.getFirstName() : existing.getFirstName();
        String position = request.getPosition() != null ? request.getPosition() : existing.getPosition();
        String phone = request.getPhone() != null ? request.getPhone() : existing.getPhone();

        responsiblePersonRepository.updateResponsiblePerson(id, lastName, firstName, position, phone);
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!responsiblePersonRepository.existsResponsiblePerson(id)) {
            throw new ResourceNotFoundException("Responsible person with id " + id + " not found");
        }
        if (responsiblePersonRepository.existsMaterialValuesByResponsiblePersonId(id)) {
            throw new ConflictException("Responsible person cannot be deleted because material values are assigned");
        }
        responsiblePersonRepository.deleteResponsiblePerson(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialValueResponse> getMaterialValues(Integer id) {
        if (!responsiblePersonRepository.existsResponsiblePerson(id)) {
            throw new ResourceNotFoundException("Responsible person with id " + id + " not found");
        }
        return responsiblePersonRepository.findMaterialValuesByResponsiblePersonId(id).stream()
                .map(materialValueMapper::toResponse)
                .toList();
    }
}
