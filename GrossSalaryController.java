package com.example.SSGS.Controller;

import com.example.SSGS.Service.GrossSalaryService;
import com.example.SSGS.Tables.GrossSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GrossSalaryController {

    @Autowired
    private GrossSalaryService grossSalaryService;

    @GetMapping("/gross-salary-form")
    public String ShowGSForm(){
        return "calculateGrossSalary";
    }
    @GetMapping("/gross-salary-form/{staff_id}")
    public String showGrossSalaryForm(@PathVariable("staff_id") int staffId, Model model){
        model.addAttribute("staffId", staffId);
        return "calculateGrossSalary";
    }

    @GetMapping("/view-gross-salary")
    public String viewGrossSalaryDetails(Model model){
        List<GrossSalary> grossSalaries = grossSalaryService.getAllGrossSalaries();
        model.addAttribute("grossSalaries", grossSalaries);
        return "viewGrossSalary";
    }

    @GetMapping("/edit-gross-salary/{staffId}")
    public String showEditForm(@PathVariable("staffId") int staffId, Model model){
        GrossSalary grossSalary = grossSalaryService.getGrossSalaryById(staffId);
        model.addAttribute("grossSalary",grossSalary);
        return "updateGrossSalary";
    }

    @GetMapping("/delete-gross-salary/{staffId}")
    public String deleteGrossSalary(@PathVariable("staffId") int staffId, RedirectAttributes redirectAttributes){
        grossSalaryService.deleteGrossSalary(staffId);
        redirectAttributes.addFlashAttribute("successMessage", "Record Deleted successfully!");
        return "redirect:/view-gross-salary";
    }

    @PostMapping("/calculate-gross-salary")
    public String calculateGrossSalary(@RequestParam("staffId") int staffId,
                                       @RequestParam("basicSalary") double basicSalary,
                                       @RequestParam(value="recalculate", required = false) boolean recalculate,
                                       Model model){
        try{
            if(basicSalary <= 0){
                throw new IllegalArgumentException("Basic Salary must be greater than 0");
            }

            boolean grossSalaryExists = grossSalaryService.grossSalaryExists(staffId);
                if (grossSalaryExists && !recalculate){
                    model.addAttribute("grossSalaryExists", true);
                    model.addAttribute("staffId", staffId);
                    model.addAttribute("basicSalary", basicSalary);
                    return "calculateGrossSalary";
                } else if (grossSalaryExists && recalculate) {
                    GrossSalary updatedGrossSalary = grossSalaryService.calculateAndSaveGrossSalary(staffId, basicSalary);
                    model.addAttribute("grossSalary", updatedGrossSalary);
                    return "displayGrossSalary";
                } else {
                    GrossSalary grossSalary = grossSalaryService.calculateAndSaveGrossSalary(staffId, basicSalary);
                    model.addAttribute("grossSalary", grossSalary);
                    return "displayGrossSalary";
                }

        } catch (Exception e){
            model.addAttribute("staffId", staffId);
            model.addAttribute("error", e.getMessage());
            return "calculateGrossSalary";
        }
    }

    @PostMapping("/update-gross-salary/{staffId}")
    public String updateGrossSalary(@PathVariable("staffId") int staffId, @ModelAttribute GrossSalary grossSalary, RedirectAttributes redirectAttributes){
        grossSalaryService.updateGrossSalary(staffId, grossSalary);
        redirectAttributes.addFlashAttribute("successMessage", "Record updated successfully!");
        return "redirect:/view-gross-salary";
    }
}
