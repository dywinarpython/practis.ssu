package org.ssu.projection;

import java.math.BigDecimal;

public interface MaterialValueProjection {
    Integer getId();
    String getName();
    String getCategory();
    BigDecimal getCost();
    String getCondition();
    Integer getResponsiblePersonId();
}
