package com.example.ISA2023.back.models.irepositories;

import com.example.ISA2023.back.models.ReservedDate;
import com.example.ISA2023.back.user.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReservedDateRepository extends JpaRepository<ReservedDate, Long > {
    //@Query("SELECT c FROM ReservedDate r WHERE r.user_id=?1")
    List<ReservedDate> findAllByUserId(Long UserId);
    void deleteById(Long reservationId);
    @Query("SELECT r FROM ReservedDate r WHERE r.CompanyId = ?1")
    public List<ReservedDate> GetByCompany(long companyId);

    @Query("SELECT r FROM ReservedDate r WHERE r.CompanyId = ?1")
    List<ReservedDate> findAllByCompanyId(long companyId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ReservedDate r WHERE r.CompanyId = ?1")
    List<ReservedDate> findAllByCompanyIdTransactional(long companyId);

}
