package com.example.ISA2023.back.models.irepositories;

import com.example.ISA2023.back.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Long > {
    Contract findByUserId(Long userId);
    List<Contract> findAllByCompanyId(Long companyId);
    void deleteById(Long userId);
}
