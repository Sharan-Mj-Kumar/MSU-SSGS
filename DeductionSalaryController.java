package com.example.SSGS.Controller;

import com.example.SSGS.Repository.CalculationRepository;
import com.example.SSGS.Repository.DeductionSalaryRepository;
import com.example.SSGS.Repository.GrossSalaryRepository;
import com.example.SSGS.Repository.UserRepository;
import com.example.SSGS.Service.DeductionSalaryService;
import com.example.SSGS.Tables.Calculation;
import com.example.SSGS.Tables.DeductionSalary;
import com.example.SSGS.Tables.GrossSalary;
import com.example.SSGS.Tables.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DeductionSalaryController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GrossSalaryRepository grossSalaryRepository;

    @Autowired
    private DeductionSalaryRepository deductionSalaryRepository;

    @Autowired
    private CalculationRepository calculationRepository;

    @Autowired
    private DeductionSalaryService deductionSalaryService;

    @GetMapping("/deduction-calculation/{staffId}")
    public String deductionCalculation(@PathVariable("staffId") int staffId,
                                       @RequestParam(value = "recalculate", required = false) Boolean recalculate,
                                       Model model) throws Exception {
        DeductionSalary deductionSalary = deductionSalaryService.getDeductionById(staffId);

        if(deductionSalary != null && (recalculate == null || !recalculate)){
            model.addAttribute("deductionSalary", deductionSalary);
            return "deduction-check";
        }else{
            Users user = userRepository.findById(staffId).orElse(null);
            GrossSalary grossSalary = grossSalaryRepository.findByStaffId(staffId);
            Calculation calculation = calculationRepository.findById(1)
                    .orElseThrow(() -> new Exception("Calculation percentage not found"));

            assert user != null;
            LocalDate dateJoined = user.getDate_joined();
            int joiningYear = dateJoined.getYear();

            double gpf = 0;
            if(grossSalary != null){
                if(joiningYear < 2003){
                    gpf = (grossSalary.getBasic() + grossSalary.getDA()) * (calculation.getGpfPercentage() / 100);
                }
            }

            double spf = 70;
            if(grossSalary != null){
                if(joiningYear < 2000){
                    spf = 20;
                }
            }


            double AUPF = 0;
            if(grossSalary != null){
                if(joiningYear > 2003){
                    AUPF = grossSalary.getCPS();
                }
            }

            if(deductionSalary == null){
                deductionSalary = new DeductionSalary();
                deductionSalary.setStaffId(staffId);
            }

            model.addAttribute("user", user);
            model.addAttribute("grossSalary", grossSalary);
            model.addAttribute("gpf", gpf);
            model.addAttribute("aupf", AUPF);
            model.addAttribute("spf", spf);
            model.addAttribute("deductionSalary", deductionSalary);

            return "deductionCalculation";
        }
    }

    @GetMapping("/deduction-check/{staffId}")
    public String deductionCheck(@PathVariable("staffId") int staffId, Model model){
        DeductionSalary deductionSalary = deductionSalaryService.getDeductionById(staffId);
        model.addAttribute("deductionSalary", deductionSalary);
        model.addAttribute("staff_id",staffId);
        return "deduction-check";
    }

    @GetMapping("/view-deduction-details")
    public String viewDeductionDetails(Model model){
        List<DeductionSalary> deductionDetails = deductionSalaryService.getAllDeductionDetails();
        model.addAttribute("deductionDetails", deductionDetails);
        return "view-deduction-details";
    }

    @GetMapping("/edit-deduction/{staffId}")
    public String editDeduction(@PathVariable("staffId") int staffId, Model model ){
        DeductionSalary deduction = deductionSalaryService.getDeductionById(staffId);
        if(deduction == null){
            model.addAttribute("Message","Deduction details not found for Staff ID: " + staffId);
            return "redirect:/view-deduction-details";
        }
        model.addAttribute("deduction", deduction);
        return "edit-deduction";
    }

    @GetMapping("/delete-deduction/{staffId}")
    public String deleteDeduction(@PathVariable("staffId") int staffId, RedirectAttributes redirectAttributes){
        deductionSalaryService.deleteDeductionById(staffId);
        redirectAttributes.addFlashAttribute("Message", "Deduction details deleted successfully!");
        return "redirect:/view-deduction-details";
    }

    @PostMapping("/store-deduction")
    public String storeDeduction(@RequestParam("staffId") int staffId,
                                 @RequestParam("CPS") double CPS,
                                 @RequestParam("GPF") double GPF,
                                 @RequestParam("AdjustmentUPF") double AdjustmentUPF,
                                 @RequestParam("IT") double IT,
                                 @RequestParam("LIC") double LIC,
                                 @RequestParam("PLI") double PLI,
                                 @RequestParam("IB") double IB,
                                 @RequestParam("NHIS") double NHIS,
                                 @RequestParam("ASSOCN") double ASSOCN,
                                 @RequestParam("PF_REC") double PF_REC,
                                 @RequestParam("FA") double FA,
                                 @RequestParam("SPF") double SPF,
                                 @RequestParam("FBF") double FBF,
                                 @RequestParam("staffQuarters") double staffQuarters,
                                 @RequestParam("salaryRecovery") double salaryRecovery,
                                 @RequestParam("coOptex") double coOptex,
                                 @RequestParam("professionalTax") double professionalTax,
                                 RedirectAttributes redirectAttributes){
        DeductionSalary deductionSalary = new DeductionSalary();
        deductionSalary.setStaffId(staffId);
        deductionSalary.setCps(CPS);
        deductionSalary.setGpf(GPF);
        deductionSalary.setAdjustmentUpf(AdjustmentUPF);
        deductionSalary.setIt(IT);
        deductionSalary.setLic(LIC);
        deductionSalary.setPli(PLI);
        deductionSalary.setIb(IB);
        deductionSalary.setNhis(NHIS);
        deductionSalary.setAssocn(ASSOCN);
        deductionSalary.setPfRec(PF_REC);
        deductionSalary.setFa(FA);
        deductionSalary.setSpf(SPF);
        deductionSalary.setFbf(FBF);
        deductionSalary.setStaffQuarters(staffQuarters);
        deductionSalary.setSalaryRecovery(salaryRecovery);
        deductionSalary.setCoOptex(coOptex);
        deductionSalary.setProfessionalTax(professionalTax);

        deductionSalaryRepository.save(deductionSalary);

        redirectAttributes.addFlashAttribute("Message", "Deduction details stored successfully!");
        return "redirect:/deduction-calculation/" + staffId + "?recalculate=true";

    }

    @PostMapping("/update-deduction/{staffId}")
    public String updateDeduction(@PathVariable("staffId") int staffId,@ModelAttribute("deduction") DeductionSalary deduction, RedirectAttributes redirectAttributes){
        deductionSalaryService.updateDeduction(staffId,deduction);
        redirectAttributes.addFlashAttribute("Message", "Deduction details updated successfully!");
        return "redirect:/view-deduction-details";
    }

    @PostMapping("/recalculate-deduction")
    public String recalculationDeduction(@RequestParam("staffId") int staffId, Model model){
        return "redirect:/deduction-calculation/" + staffId + "?recalculate=true";
    }
}
