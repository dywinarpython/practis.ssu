package org.ssu.projection;

import org.ssu.enums.EntityStatus;

import java.math.BigDecimal;

public interface MaterialValueProjection {
    Integer getId();
    String getName();
    String getCategory();
    EntityStatus getStatus();
    BigDecimal getValue();
    BigDecimal getCost();
    String getCondition();
    Integer getResponsiblePersonId();
    Integer getWarehouseId();
}
