package org.ssu.repository;

import org.ssu.entity.ValueTransfer;
import org.ssu.projection.ValueTransferProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ValueTransferRepository extends JpaRepository<ValueTransfer, Integer> {

    @Query(value = """
            SELECT
                transfer_id AS id,
                from_responsible_person_id AS fromResponsiblePersonId,
                to_responsible_person_id AS toResponsiblePersonId,
                material_value_id AS materialValueId,
                date AS date
            FROM value_transfers
            ORDER BY transfer_id
            """, nativeQuery = true)
    List<ValueTransferProjection> findAllValueTransfers();

    @Query(value = """
            SELECT
                transfer_id AS id,
                from_responsible_person_id AS fromResponsiblePersonId,
                to_responsible_person_id AS toResponsiblePersonId,
                material_value_id AS materialValueId,
                date AS date
            FROM value_transfers
            WHERE transfer_id = :id
            """, nativeQuery = true)
    Optional<ValueTransferProjection> findValueTransferById(@Param("id") Integer id);

    @Query(value = """
            INSERT INTO value_transfers(from_responsible_person_id, to_responsible_person_id, material_value_id, date)
            VALUES (:fromId, :toId, :materialValueId, :date)
            RETURNING transfer_id
            """, nativeQuery = true)
    Integer insertValueTransfer(@Param("fromId") Integer fromId,
                                 @Param("toId") Integer toId,
                                 @Param("materialValueId") Integer materialValueId,
                                 @Param("date") LocalDateTime date);

    @Modifying
    @Query(value = """
            UPDATE value_transfers
            SET from_responsible_person_id = :fromId,
                to_responsible_person_id = :toId,
                material_value_id = :materialValueId,
                date = :date
            WHERE transfer_id = :id
            """, nativeQuery = true)
    int updateValueTransfer(@Param("id") Integer id,
                             @Param("fromId") Integer fromId,
                             @Param("toId") Integer toId,
                             @Param("materialValueId") Integer materialValueId,
                             @Param("date") LocalDateTime date);

    @Modifying
    @Query(value = """
            DELETE FROM value_transfers
            WHERE transfer_id = :id
            """, nativeQuery = true)
    int deleteValueTransfer(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM value_transfers WHERE transfer_id = :id
            )
            """, nativeQuery = true)
    boolean existsValueTransfer(@Param("id") Integer id);
}
