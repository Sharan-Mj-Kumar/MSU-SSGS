package com.example.SSGS.Service;

import com.example.SSGS.Repository.DeductionSalaryRepository;
import com.example.SSGS.Tables.DeductionSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeductionSalaryService {

    @Autowired
    private DeductionSalaryRepository deductionSalaryRepository;

    public List<DeductionSalary> getAllDeductionDetails(){
        return deductionSalaryRepository.findAll();
    }

    public DeductionSalary getDeductionById(int staffId){
        Optional<DeductionSalary> deduction = deductionSalaryRepository.findById(staffId);
        return deduction.orElse(null);
    }

    public void updateDeduction(int staffId, DeductionSalary updatedDeductionSalary) {

        DeductionSalary existingDeductionSalary = deductionSalaryRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("DeductionSalary not found for staff_id: " + staffId));

        existingDeductionSalary.setCps(updatedDeductionSalary.getCps());
        existingDeductionSalary.setGpf(updatedDeductionSalary.getGpf());
        existingDeductionSalary.setAdjustmentUpf(updatedDeductionSalary.getAdjustmentUpf());
        existingDeductionSalary.setIt(updatedDeductionSalary.getIt());
        existingDeductionSalary.setLic(updatedDeductionSalary.getLic());
        existingDeductionSalary.setPli(updatedDeductionSalary.getPli());
        existingDeductionSalary.setIb(updatedDeductionSalary.getIb());
        existingDeductionSalary.setNhis(updatedDeductionSalary.getNhis());
        existingDeductionSalary.setAssocn(updatedDeductionSalary.getAssocn());
        existingDeductionSalary.setPfRec(updatedDeductionSalary.getPfRec());
        existingDeductionSalary.setFa(updatedDeductionSalary.getFa());
        existingDeductionSalary.setSpf(updatedDeductionSalary.getSpf());
        existingDeductionSalary.setFbf(updatedDeductionSalary.getFbf());
        existingDeductionSalary.setStaffQuarters(updatedDeductionSalary.getStaffQuarters());
        existingDeductionSalary.setSalaryRecovery(updatedDeductionSalary.getSalaryRecovery());
        existingDeductionSalary.setCoOptex(updatedDeductionSalary.getCoOptex());
        existingDeductionSalary.setProfessionalTax(updatedDeductionSalary.getProfessionalTax());

        deductionSalaryRepository.save(existingDeductionSalary);
    }

    public void deleteDeductionById(int staffId){
        deductionSalaryRepository.deleteById(staffId);
    }
}