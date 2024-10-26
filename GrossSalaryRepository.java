package com.example.SSGS.Repository;

import com.example.SSGS.Tables.GrossSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrossSalaryRepository extends JpaRepository<GrossSalary, Integer> {

    GrossSalary findByStaffId(int staffId);
    boolean existsById(int staffId);
}

