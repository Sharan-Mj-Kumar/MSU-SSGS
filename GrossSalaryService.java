package com.example.SSGS.Service;

import com.example.SSGS.Repository.CalculationRepository;
import com.example.SSGS.Repository.GrossSalaryRepository;
import com.example.SSGS.Repository.UserRepository;
import com.example.SSGS.Tables.Calculation;
import com.example.SSGS.Tables.GrossSalary;
import com.example.SSGS.Tables.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GrossSalaryService {

    @Autowired
    private GrossSalaryRepository grossSalaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalculationRepository calculationRepository;

    public GrossSalary calculateAndSaveGrossSalary(int staffId, double basicSalary) throws Exception {

        Calculation calculation = calculationRepository.findById(1)
                .orElseThrow(() -> new Exception("Calculation percentage not found"));
        // Check if staffId exists
        Users user = userRepository.findById(staffId)
                .orElseThrow(() -> new Exception("Staff ID not found"));

        GrossSalary grossSalary;

        if(grossSalaryRepository.existsById(staffId)){
            grossSalary = grossSalaryRepository.findByStaffId(staffId);
        }else{
            grossSalary = new GrossSalary();
            grossSalary.setStaffId(staffId);
        }

        grossSalary.setBasic(basicSalary);

        // Calculate DA (50% of basic)
        double DA = basicSalary * (calculation.getDaPercentage() / 100);
        grossSalary.setDA(DA);

        // Calculate HRA based on slabs
        double HRA = calculateHRA(basicSalary);
        grossSalary.setHRA(HRA);

        // Calculate CCA based on slabs
        double CCA = calculateCCA(basicSalary);
        grossSalary.setCCA(CCA);

        // Set MA (fixed at 300)
        double MA = 300;
        grossSalary.setMA(MA);

        // Check the user's date_joined year and decide whether to calculate CPS
        LocalDate dateJoined = user.getDate_joined();
        int joiningYear = dateJoined.getYear();

        double CPS = 0.0;
        if (joiningYear >= 2003) {
            CPS = (basicSalary + DA) * (calculation.getCpsPercentage() / 100);
        }
        grossSalary.setCPS(CPS);

        // Save to database
        return grossSalaryRepository.save(grossSalary);
    }

    private double calculateHRA(double basicSalary) {
        if (basicSalary <= 13600) {
            return 600;
        } else if (basicSalary <= 17200) {
            return 700;
        } else if (basicSalary <= 21000) {
            return 800;
        } else if (basicSalary <= 23900) {
            return 1000;
        } else if (basicSalary <= 27200) {
            return 1200;
        } else if (basicSalary <= 30600) {
            return 1500;
        } else if (basicSalary <= 35400) {
            return 1700;
        } else if (basicSalary <= 37300) {
            return 1800;
        } else if (basicSalary <= 41100) {
            return 2300;
        } else if (basicSalary <= 44500) {
            return 2600;
        } else if (basicSalary <= 50200) {
            return 2900;
        } else if (basicSalary <= 51600) {
            return 3100;
        } else if (basicSalary <= 54000) {
            return 3200;
        } else if (basicSalary <= 55500) {
            return 3200;
        } else if (basicSalary <= 56900) {
            return 3200;
        } else if (basicSalary <= 64200) {
            return 3200;
        } else {
            return 3200;
        }
    }

    private double calculateCCA(double basicSalary) {
        if (basicSalary < 20600) {
            return 180;
        } else if (basicSalary <= 30800) {
            return 260;
        } else if (basicSalary <= 41100) {
            return 400;
        } else {
            return 720;
        }
    }

    public boolean grossSalaryExists(int staffId){
        return grossSalaryRepository.existsById(staffId);
    }
    public List<GrossSalary> getAllGrossSalaries() {
        return grossSalaryRepository.findAll();
    }

    public GrossSalary getGrossSalaryById(int staffId) {
        return grossSalaryRepository.findById(staffId).orElseThrow(() -> new RuntimeException("Gross Salary not found"));
    }

    public void deleteGrossSalary(int staffId) {
        grossSalaryRepository.deleteById(staffId);
    }

    public void updateGrossSalary(int staffId, GrossSalary updatedGrossSalary) {
        GrossSalary existingGrossSalary = getGrossSalaryById(staffId);
        existingGrossSalary.setBasic(updatedGrossSalary.getBasic());
        existingGrossSalary.setDA(updatedGrossSalary.getDA());
        existingGrossSalary.setHRA(updatedGrossSalary.getHRA());
        existingGrossSalary.setCCA(updatedGrossSalary.getCCA());
        existingGrossSalary.setMA(updatedGrossSalary.getMA());
        existingGrossSalary.setCPS(updatedGrossSalary.getCPS());
        grossSalaryRepository.save(existingGrossSalary);
    }
}

