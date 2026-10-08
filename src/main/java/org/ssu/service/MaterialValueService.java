package org.ssu.service;

import org.ssu.dto.request.MaterialValueCreateRequest;
import org.ssu.dto.request.MaterialValuePatchRequest;
import org.ssu.dto.request.MaterialValueUpdateRequest;
import org.ssu.dto.response.MaterialValueResponse;
import org.ssu.dto.response.MovementResponse;
import org.ssu.dto.response.ValueTransferResponse;

import java.util.List;

public interface MaterialValueService {

    List<MaterialValueResponse> getAll();

    MaterialValueResponse getById(Integer id);

    MaterialValueResponse create(MaterialValueCreateRequest request);

    MaterialValueResponse update(Integer id, MaterialValueUpdateRequest request);

    MaterialValueResponse patch(Integer id, MaterialValuePatchRequest request);

    void delete(Integer id);

    List<MovementResponse> getMovements(Integer id);

    List<ValueTransferResponse> getTransfers(Integer id);
}
