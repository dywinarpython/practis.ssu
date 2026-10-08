package org.ssu.service;

import org.ssu.dto.request.MovementCreateRequest;
import org.ssu.dto.request.MovementPatchRequest;
import org.ssu.dto.request.MovementUpdateRequest;
import org.ssu.dto.response.MovementResponse;

import java.util.List;

public interface MovementService {

    List<MovementResponse> getAll();

    MovementResponse getById(Integer id);

    MovementResponse create(MovementCreateRequest request);

    MovementResponse update(Integer id, MovementUpdateRequest request);

    MovementResponse patch(Integer id, MovementPatchRequest request);

    void delete(Integer id);
}
