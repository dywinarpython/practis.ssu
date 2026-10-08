package org.ssu.repository;

import org.ssu.entity.Warehouse;
import org.ssu.projection.MaterialValueProjection;
import org.ssu.projection.WarehouseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query(value = """
            SELECT
                warehouse_id AS id,
                name AS name,
                address AS address, 
                status as status
            FROM warehouses
            ORDER BY warehouse_id
            """, nativeQuery = true)
    List<WarehouseProjection> findAllWarehouses();

    @Query(value = """
            SELECT
                warehouse_id AS id,
                name AS name,
                address AS address,
                status as status
            FROM warehouses
            WHERE warehouse_id = :id
            """, nativeQuery = true)
    Optional<WarehouseProjection> findWarehouseById(@Param("id") Integer id);

    @Query(value = """
            SELECT
                material_value_id AS id,
                name AS name,
                category AS category,
                status as status,
                cost AS cost,
                condition AS condition,
                responsible_person_id AS responsiblePersonId
            FROM material_values
            WHERE warehouse_id = :id
            """, nativeQuery = true)
    List<MaterialValueProjection> findMaterialValueById(@Param("id") Integer id);

    @Query(value = """
            INSERT INTO warehouses(name, address, status)
            VALUES (:name, :address, :status)
            RETURNING warehouse_id
            """, nativeQuery = true)
    Integer insertWarehouse(@Param("name") String name, @Param("address") String address, @Param("status") String status);

    @Modifying
    @Query(value = """
            UPDATE warehouses
            SET name = :name,
                address = :address
            WHERE warehouse_id = :id
            """, nativeQuery = true)
    int updateWarehouse(@Param("id") Integer id, @Param("name") String name, @Param("address") String address);

    @Modifying
    @Query(value = """
            UPDATE  warehouses
            SET  status = 'DELETED'
            WHERE warehouse_id = :id
            """, nativeQuery = true)
    int deleteWarehouse(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM warehouses WHERE warehouse_id = :id and status <> 'DELETED'
            )
            """, nativeQuery = true)
    boolean existsWarehouse(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM material_values WHERE warehouse_id = :warehouseId
            )
            """, nativeQuery = true)
    boolean existsMovementsByWarehouseId(@Param("warehouseId") Integer warehouseId);
}
