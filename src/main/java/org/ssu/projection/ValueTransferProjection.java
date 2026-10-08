package org.ssu.projection;

import java.time.LocalDateTime;

public interface ValueTransferProjection {
    Integer getId();
    Integer getFromResponsiblePersonId();
    Integer getToResponsiblePersonId();
    Integer getMaterialValueId();
    LocalDateTime getDate();
}
