package org.ssu.repository;

import org.ssu.entity.Movement;
import org.ssu.projection.MovementProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Integer> {

    @Query(value = """
            SELECT
                movement_id AS id,
                warehouse_id AS warehouseId,
                movement_type AS movementType,
                status AS status,
                date AS date,
                material_value_id AS materialValueId
            FROM movements
            ORDER BY movement_id
            """, nativeQuery = true)
    List<MovementProjection> findAllMovements();

    @Query(value = """
            SELECT
                movement_id AS id,
                warehouse_id AS warehouseId,
                movement_type AS movementType,
                status AS status,
                date AS date,
                material_value_id AS materialValueId
            FROM movements
            WHERE movement_id = :id
            """, nativeQuery = true)
    Optional<MovementProjection> findMovementById(@Param("id") Integer id);

    @Query(value = """
            INSERT INTO movements(warehouse_id, movement_type, status, date, material_value_id)
            VALUES (:warehouseId, :movementType, :status, :date, :materialValueId)
            RETURNING movement_id
            """, nativeQuery = true)
    Integer insertMovement(@Param("warehouseId") Integer warehouseId,
                            @Param("movementType") String movementType,
                            @Param("status") String status,
                            @Param("date") LocalDateTime date,
                            @Param("materialValueId") Integer materialValueId);

    @Modifying
    @Query(value = """
            UPDATE movements
            SET warehouse_id = :warehouseId,
                movement_type = :movementType,
                status = :status,
                date = :date,
                material_value_id = :materialValueId
            WHERE movement_id = :id
            """, nativeQuery = true)
    int updateMovement(@Param("id") Integer id,
                        @Param("warehouseId") Integer warehouseId,
                        @Param("movementType") String movementType,
                        @Param("status") String status,
                        @Param("date") LocalDateTime date,
                        @Param("materialValueId") Integer materialValueId);

    @Modifying
    @Query(value = """
            DELETE FROM movements
            WHERE movement_id = :id
            """, nativeQuery = true)
    int deleteMovement(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM movements WHERE movement_id = :id
            )
            """, nativeQuery = true)
    boolean existsMovement(@Param("id") Integer id);
}
