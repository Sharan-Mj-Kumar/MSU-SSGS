package com.example.SSGS.Service;

import com.example.SSGS.Repository.CalculationRepository;
import com.example.SSGS.Tables.Calculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculationService {

    @Autowired
    private CalculationRepository calculationRepository;

    public List<Calculation> getCalculationDetails() {
        return calculationRepository.findAll();
    }

    public Calculation getCalculationById(int id) {
        return calculationRepository.findById(id).orElse(null);
    }

    public Calculation updateCalculation(int id, Calculation updatedCalculation) {
        Calculation existingCalculation = calculationRepository.findById(id).orElse(null);
        if (existingCalculation != null) {
            existingCalculation.setDaPercentage(updatedCalculation.getDaPercentage());
            existingCalculation.setCpsPercentage(updatedCalculation.getCpsPercentage());
            existingCalculation.setGpfPercentage(updatedCalculation.getGpfPercentage());
            return calculationRepository.save(existingCalculation);
        }
        return null;
    }
}

