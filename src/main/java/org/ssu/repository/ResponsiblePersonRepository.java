package org.ssu.repository;

import org.ssu.entity.ResponsiblePerson;
import org.ssu.projection.MaterialValueProjection;
import org.ssu.projection.ResponsiblePersonProjection;
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
                phone AS phone, 
                status as status
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
                phone AS phone, 
                status as status
            FROM responsible_persons
            WHERE responsible_person_id = :id
            """, nativeQuery = true)
    Optional<ResponsiblePersonProjection> findResponsiblePersonById(@Param("id") Integer id);

    @Query(value = """
            INSERT INTO responsible_persons(last_name, first_name, position, phone, status)
            VALUES (:lastName, :firstName, :position, :phone, :status)
            RETURNING responsible_person_id
            """, nativeQuery = true)
    Integer insertResponsiblePerson(@Param("lastName") String lastName,
                                     @Param("firstName") String firstName,
                                     @Param("position") String position,
                                     @Param("phone") String phone, @Param("status") String status);

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
            UPDATE  responsible_persons
            SET status = 'DELETED'            
            WHERE responsible_person_id = :id
            """, nativeQuery = true)
    int deleteResponsiblePerson(@Param("id") Integer id);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM responsible_persons WHERE responsible_person_id = :id and status <> 'DELETED'
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
                status AS status,            
                cost AS cost,
                condition AS condition,
                responsible_person_id AS responsiblePersonId
            FROM material_values
            WHERE responsible_person_id = :id
            ORDER BY material_value_id
            """, nativeQuery = true)
    List<MaterialValueProjection> findMaterialValuesByResponsiblePersonId(@Param("id") Integer id);

}
