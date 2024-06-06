package com.example.ISA2023.back.models.irepositories;

import com.example.ISA2023.back.models.PredefinedDate;
import com.example.ISA2023.back.models.ReservedDate;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPredefinedDateRepository extends JpaRepository<PredefinedDate, Long > {
    @Query("SELECT r FROM PredefinedDate r WHERE r.id IN ?1")
    public List<PredefinedDate> GetByIds(List<Long> id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM PredefinedDate r")
    List<PredefinedDate> findAllTransactional();
}
