package org.ssu.service;

import org.ssu.dto.request.ResponsiblePersonCreateRequest;
import org.ssu.dto.request.ResponsiblePersonPatchRequest;
import org.ssu.dto.request.ResponsiblePersonUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.ResponsiblePersonResponse;
import org.ssu.dto.response.ValueTransferResponse;

import java.util.List;

public interface ResponsiblePersonService {

    List<ResponsiblePersonResponse> getAll();

    ResponsiblePersonResponse getById(Integer id);

    ResponsiblePersonResponse create(ResponsiblePersonCreateRequest request);

    ResponsiblePersonResponse update(Integer id, ResponsiblePersonUpdateRequest request);

    ResponsiblePersonResponse patch(Integer id, ResponsiblePersonPatchRequest request);

    void delete(Integer id);

    List<MaterialValueResponse> getMaterialValues(Integer id);

}
