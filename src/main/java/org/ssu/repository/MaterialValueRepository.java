package org.ssu.repository;

import org.ssu.entity.MaterialValue;
import org.ssu.projection.MaterialValueProjection;
import org.ssu.projection.MovementProjection;
import org.ssu.projection.ValueTransferProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MaterialValueRepository extends JpaRepository<MaterialValue, Integer> {

    @Query(value = """
            SELECT
                material_value_id AS id,
                name AS name,
                category AS category,
                status as status,            
                cost AS cost,
                condition AS condition,
                responsible_person_id AS responsiblePersonId, 
                warehouse_id as warehouseId            
            FROM material_values
            ORDER BY material_value_id
            """, nativeQuery = true)
    List<MaterialValueProjection> findAllMaterialValues();

    @Query(value = """
            SELECT
                material_value_id AS id,
                name AS name,
                category AS category,
                status as status,
                cost AS cost,
                condition AS condition,
                responsible_person_id AS responsiblePersonId, 
                warehouse_id as warehouseId
            FROM material_values
            WHERE material_value_id = :id
            """, nativeQuery = true)
    Optional<MaterialValueProjection> findMaterialValueById(@Param("id") Integer id);

    @Query(value = """
            INSERT INTO material_values(name, category, cost, status, condition, responsible_person_id)
            VALUES (:name, :category, :cost, :status, :condition, :responsiblePersonId)
            RETURNING material_value_id
            """, nativeQuery = true)
    Integer insertMaterialValue(@Param("name") String name,
                                 @Param("category") String category,
                                 @Param("cost") BigDecimal cost,
                                 @Param("status") String status,
                                 @Param("condition") String condition,
                                 @Param("responsiblePersonId") Integer responsiblePersonId);

    @Modifying
    @Query(value = """
            UPDATE material_values
            SET name = :name,
                category = :category,
                cost = :cost,
                condition = :condition
            WHERE material_value_id = :id
            """, nativeQuery = true)
    int updateMaterialValue(@Param("id") Integer id,
                             @Param("name") String name,
                             @Param("category") String category,
                             @Param("cost") BigDecimal cost,
                             @Param("condition") String condition);

    @Modifying
    @Query(value = """
            UPDATE material_values           
            SET status = 'DELETED'
            WHERE material_value_id = :id
            """, nativeQuery = true)
    int deleteMaterialValue(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM material_values WHERE material_value_id = :id and status <> 'DELETED'
            )
            """, nativeQuery = true)
    boolean existsMaterialValue(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM material_values WHERE material_value_id = :id 
                         and status <> 'DELETED' 
                         and responsible_person_id = :responsiblePersonId
            )
            """, nativeQuery = true)
    boolean existsMaterialValueAndResponsiblePersonId(@Param("id") Integer id, @Param("responsiblePersonId") Integer responsiblePersonId);


    @Query(value = """
            SELECT
                movement_id AS id,
                warehouse_id AS warehouseId,
                movement_type AS movementType,
                status AS status,
                date AS date,
                material_value_id AS materialValueId
            FROM movements
            WHERE material_value_id = :id
            ORDER BY movement_id
            """, nativeQuery = true)
    List<MovementProjection> findMovementsByMaterialValueId(@Param("id") Integer id);

    @Query(value = """
            SELECT
                transfer_id AS id,
                from_responsible_person_id AS fromResponsiblePersonId,
                to_responsible_person_id AS toResponsiblePersonId,
                material_value_id AS materialValueId,
                date AS date
            FROM value_transfers
            WHERE material_value_id = :id
            ORDER BY transfer_id
            """, nativeQuery = true)
    List<ValueTransferProjection> findTransfersByMaterialValueId(@Param("id") Integer id);
}
