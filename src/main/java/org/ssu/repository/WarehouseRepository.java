package org.ssu.repository;

import org.ssu.entity.Warehouse;
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
                address AS address
            FROM warehouses
            ORDER BY warehouse_id
            """, nativeQuery = true)
    List<WarehouseProjection> findAllWarehouses();

    @Query(value = """
            SELECT
                warehouse_id AS id,
                name AS name,
                address AS address
            FROM warehouses
            WHERE warehouse_id = :id
            """, nativeQuery = true)
    Optional<WarehouseProjection> findWarehouseById(@Param("id") Integer id);

    @Query(value = """
            INSERT INTO warehouses(name, address)
            VALUES (:name, :address)
            RETURNING warehouse_id
            """, nativeQuery = true)
    Integer insertWarehouse(@Param("name") String name, @Param("address") String address);

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
            DELETE FROM warehouses
            WHERE warehouse_id = :id
            """, nativeQuery = true)
    int deleteWarehouse(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM warehouses WHERE warehouse_id = :id
            )
            """, nativeQuery = true)
    boolean existsWarehouse(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM movements WHERE warehouse_id = :warehouseId
            )
            """, nativeQuery = true)
    boolean existsMovementsByWarehouseId(@Param("warehouseId") Integer warehouseId);
}
