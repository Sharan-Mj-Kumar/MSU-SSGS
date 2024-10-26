package com.example.SSGS.Repository;

import com.example.SSGS.Tables.DeductionSalary;
import com.example.SSGS.Tables.GrossSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeductionSalaryRepository extends JpaRepository<DeductionSalary, Integer> {
    DeductionSalary findByStaffId(int staffId);
}
