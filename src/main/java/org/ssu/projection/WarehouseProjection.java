package org.ssu.projection;

import org.ssu.enums.EntityStatus;

public interface WarehouseProjection {
    Integer getId();
    String getName();
    String getAddress();
    EntityStatus getStatus();
}
