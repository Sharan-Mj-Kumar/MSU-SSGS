package com.example.SSGS.Controller;

import com.example.SSGS.Service.CalculationService;
import com.example.SSGS.Tables.Calculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CalculationController {

    @Autowired
    private CalculationService calculationService;

    // View Calc-Percentage Details
    @GetMapping("/calc-percentage")
    public String viewCalculationDetails(Model model) {
        model.addAttribute("calculationDetails", calculationService.getCalculationDetails());
        return "calc-percentage";
    }

    // Edit Calc-Percentage Details
    @GetMapping("/edit-calc/{id}")
    public String editCalculationForm(@PathVariable("id") int id, Model model) {
        Calculation calculation = calculationService.getCalculationById(id);
        model.addAttribute("calculation", calculation);
        return "edit-calc";
    }

    // Save updated Calc-Percentage Details
    @PostMapping("/update-calc/{id}")
    public String updateCalculation(@PathVariable("id") int id, @ModelAttribute("calculation") Calculation updatedCalculation) {
        calculationService.updateCalculation(id, updatedCalculation);
        return "redirect:/admin/calc-percentage";
    }
}

