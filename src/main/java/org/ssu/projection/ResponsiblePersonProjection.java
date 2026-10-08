package org.ssu.projection;

import org.ssu.enums.EntityStatus;

public interface ResponsiblePersonProjection {
    Integer getId();
    String getLastName();
    String getFirstName();
    String getPosition();
    String getPhone();
    EntityStatus getStatus();
}
