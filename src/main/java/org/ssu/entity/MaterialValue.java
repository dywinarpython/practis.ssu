package org.ssu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ssu.enums.EntityStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "material_values")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_value_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntityStatus status;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "responsible_person_id", nullable = false)
    private Integer responsiblePersonId;

    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;
}
