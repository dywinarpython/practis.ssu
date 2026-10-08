package org.ssu.service;

import org.ssu.dto.request.ValueTransferCreateRequest;
import org.ssu.dto.request.ValueTransferPatchRequest;
import org.ssu.dto.request.ValueTransferUpdateRequest;
import org.ssu.dto.response.ValueTransferResponse;

import java.util.List;

public interface ValueTransferService {

    List<ValueTransferResponse> getAll();

    ValueTransferResponse getById(Integer id);

    ValueTransferResponse create(ValueTransferCreateRequest request);

    ValueTransferResponse update(Integer id, ValueTransferUpdateRequest request);

    ValueTransferResponse patch(Integer id, ValueTransferPatchRequest request);

    void delete(Integer id);
}
