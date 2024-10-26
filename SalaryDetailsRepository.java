package com.example.SSGS.Repository;

import com.example.SSGS.Tables.SalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryDetailsRepository extends JpaRepository<SalaryDetails, Integer> {
    SalaryDetails findByStaffId(int staffId);
    SalaryDetails findByStaffIdAndSalaryMonth(Integer staffId, String salaryMonth);
    List<SalaryDetails> findByDeptSection(String deptSection);
}
