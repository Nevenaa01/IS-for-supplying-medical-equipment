package com.example.ISA2023.back.models.irepositories;

import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.models.Equipment;
import com.example.ISA2023.back.models.EquipmentType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEquipmentRepository extends JpaRepository<Equipment, Long > {
    @Query("SELECT e FROM Equipment e WHERE e.grade>?1")
    List<Equipment> findEquipmentByGrade(double rating);
    @Query("SELECT e FROM Equipment e WHERE e.type=?1")
    List<Equipment> findEquipmentByEquipmentType(EquipmentType et);
    @Query("SELECT e FROM Equipment e WHERE LOWER(e.name) LIKE ?1%")
    List<Equipment> findEqupmentByName(String name);

    @Query("SELECT e From Equipment e WHERE e.companyId = ?1")
    List<Equipment> findEquipmentByCompanyId(Long id);
    @Query("SELECT e FROM Equipment e WHERE LOWER(e.name) LIKE LOWER(CONCAT(?1, '%'))")
    List<Equipment> findEquipmentByName(String name);
    @Query(value = "SELECT * FROM Equipment e WHERE :companyId = ANY(e.company_id)", nativeQuery = true)
    List<Equipment> findEquipmentByCompany( Long companyId);
    @Query("SELECT e FROM Equipment e")
    List<Equipment> GetAllEquipment();

    @Query("SELECT e FROM Equipment e WHERE e.id=?1")
    Equipment GetEquipmentById(Long id);
    @Query("SELECT e FROM Equipment e WHERE LOWER(e.name) LIKE ?1% AND e.grade>=?2")
    List<Equipment> findEquipmentByNameAndRating(String name, double rating);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Equipment e WHERE e.id=?1")
    Equipment GetEquipmentByIdTransactional(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Equipment e WHERE e.id=?1")
    Optional<Equipment> findByIdTransactional(Long id);
}
