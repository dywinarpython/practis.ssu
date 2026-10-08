package org.ssu.repository;

import org.ssu.entity.ResponsiblePerson;
import org.ssu.projection.MaterialValueProjection;
import org.ssu.projection.ResponsiblePersonProjection;
import org.ssu.projection.ValueTransferProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Integer> {

    @Query(value = """
            SELECT
                responsible_person_id AS id,
                last_name AS lastName,
                first_name AS firstName,
                position AS position,
                phone AS phone
            FROM responsible_persons
            ORDER BY responsible_person_id
            """, nativeQuery = true)
    List<ResponsiblePersonProjection> findAllResponsiblePersons();

    @Query(value = """
            SELECT
                responsible_person_id AS id,
                last_name AS lastName,
                first_name AS firstName,
                position AS position,
                phone AS phone
            FROM responsible_persons
            WHERE responsible_person_id = :id
            """, nativeQuery = true)
    Optional<ResponsiblePersonProjection> findResponsiblePersonById(@Param("id") Integer id);

    @Query(value = """
            INSERT INTO responsible_persons(last_name, first_name, position, phone)
            VALUES (:lastName, :firstName, :position, :phone)
            RETURNING responsible_person_id
            """, nativeQuery = true)
    Integer insertResponsiblePerson(@Param("lastName") String lastName,
                                     @Param("firstName") String firstName,
                                     @Param("position") String position,
                                     @Param("phone") String phone);

    @Modifying
    @Query(value = """
            UPDATE responsible_persons
            SET last_name = :lastName,
                first_name = :firstName,
                position = :position,
                phone = :phone
            WHERE responsible_person_id = :id
            """, nativeQuery = true)
    int updateResponsiblePerson(@Param("id") Integer id,
                                 @Param("lastName") String lastName,
                                 @Param("firstName") String firstName,
                                 @Param("position") String position,
                                 @Param("phone") String phone);

    @Modifying
    @Query(value = """
            DELETE FROM responsible_persons
            WHERE responsible_person_id = :id
            """, nativeQuery = true)
    int deleteResponsiblePerson(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM responsible_persons WHERE responsible_person_id = :id
            )
            """, nativeQuery = true)
    boolean existsResponsiblePerson(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM material_values WHERE responsible_person_id = :id
            )
            """, nativeQuery = true)
    boolean existsMaterialValuesByResponsiblePersonId(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM value_transfers WHERE from_responsible_person_id = :id
            )
            """, nativeQuery = true)
    boolean existsTransfersFromResponsiblePersonId(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM value_transfers WHERE to_responsible_person_id = :id
            )
            """, nativeQuery = true)
    boolean existsTransfersToResponsiblePersonId(@Param("id") Integer id);

    @Query(value = """
            SELECT
                material_value_id AS id,
                name AS name,
                category AS category,
                cost AS cost,
                condition AS condition,
                responsible_person_id AS responsiblePersonId
            FROM material_values
            WHERE responsible_person_id = :id
            ORDER BY material_value_id
            """, nativeQuery = true)
    List<MaterialValueProjection> findMaterialValuesByResponsiblePersonId(@Param("id") Integer id);

    @Query(value = """
            SELECT
                transfer_id AS id,
                from_responsible_person_id AS fromResponsiblePersonId,
                to_responsible_person_id AS toResponsiblePersonId,
                material_value_id AS materialValueId,
                date AS date
            FROM value_transfers
            WHERE from_responsible_person_id = :id
            ORDER BY transfer_id
            """, nativeQuery = true)
    List<ValueTransferProjection> findTransfersGivenByResponsiblePersonId(@Param("id") Integer id);

    @Query(value = """
            SELECT
                transfer_id AS id,
                from_responsible_person_id AS fromResponsiblePersonId,
                to_responsible_person_id AS toResponsiblePersonId,
                material_value_id AS materialValueId,
                date AS date
            FROM value_transfers
            WHERE to_responsible_person_id = :id
            ORDER BY transfer_id
            """, nativeQuery = true)
    List<ValueTransferProjection> findTransfersReceivedByResponsiblePersonId(@Param("id") Integer id);
}
