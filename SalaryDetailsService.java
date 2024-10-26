package com.example.SSGS.Service;

import com.example.SSGS.Repository.SalaryDetailsRepository;
import com.example.SSGS.Tables.SalaryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalaryDetailsService {

    @Autowired
    private SalaryDetailsRepository salaryDetailsRepository;

    public List<SalaryDetails> getAllSalaryDetails() {
        return salaryDetailsRepository.findAll();
    }

    public void deleteSalaryDetailsById(Integer staffId) {
        salaryDetailsRepository.deleteById(staffId);
    }

    public SalaryDetails findByStaffIdAndSalaryMonth(Integer staffId, String salaryMonth) {
        return salaryDetailsRepository.findByStaffIdAndSalaryMonth(staffId, salaryMonth);
    }

    public List<SalaryDetails> getSalaryReportByDepartment(String deptSection) {
        return salaryDetailsRepository.findByDeptSection(deptSection);
    }

    public BigDecimal getTotalNetPayByDepartment(String deptSection) {
        List<SalaryDetails> details = salaryDetailsRepository.findByDeptSection(deptSection);
        return details.stream()
                .map(SalaryDetails::getNetPay)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

