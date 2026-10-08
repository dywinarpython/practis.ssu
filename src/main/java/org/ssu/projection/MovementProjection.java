package org.ssu.projection;

import java.time.LocalDateTime;

public interface MovementProjection {
    Integer getId();
    Integer getWarehouseId();
    String getMovementType();
    String getStatus();
    LocalDateTime getDate();
    Integer getMaterialValueId();
}
