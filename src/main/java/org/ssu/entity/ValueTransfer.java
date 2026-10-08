package org.ssu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "value_transfers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValueTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Integer id;

    @Column(name = "from_responsible_person_id")
    private Integer fromResponsiblePersonId;

    @Column(name = "to_responsible_person_id")
    private Integer toResponsiblePersonId;

    @Column(name = "material_value_id", nullable = false)
    private Integer materialValueId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
