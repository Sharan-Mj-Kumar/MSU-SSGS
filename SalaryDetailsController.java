package com.example.SSGS.Controller;

import com.example.SSGS.Repository.DeductionSalaryRepository;
import com.example.SSGS.Repository.GrossSalaryRepository;
import com.example.SSGS.Repository.SalaryDetailsRepository;
import com.example.SSGS.Repository.UserRepository;
import com.example.SSGS.Service.SalaryDetailsService;
import com.example.SSGS.Tables.DeductionSalary;
import com.example.SSGS.Tables.GrossSalary;
import com.example.SSGS.Tables.SalaryDetails;
import com.example.SSGS.Tables.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;


@Controller
public class SalaryDetailsController {

    @Autowired
    private GrossSalaryRepository grossSalaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeductionSalaryRepository deductionSalaryRepository;

    @Autowired
    private SalaryDetailsRepository salaryDetailsRepository;

    @Autowired
    private SalaryDetailsService salaryDetailsService;


    @GetMapping("/generate-slip/{staffId}")
    public String generateSalarySlip(@PathVariable("staffId") int staffId, @RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        session.setAttribute("userEmailId", email);

        Users user = userRepository.findById(staffId).orElse(null);
        GrossSalary grossSalary = grossSalaryRepository.findByStaffId(staffId);
        DeductionSalary deductionSalary = deductionSalaryRepository.findByStaffId(staffId);

        if (user != null && grossSalary != null && deductionSalary != null) {
            model.addAttribute("staffId", staffId);
            model.addAttribute("name", user.getName());
            model.addAttribute("designation", user.getDesignation());
            model.addAttribute("deptSection", user.getDept_section());
            model.addAttribute("email", email);
            model.addAttribute("grossSalary", grossSalary);
            model.addAttribute("deductionSalary", deductionSalary);
            return "salary-bill";
        } else {
            redirectAttributes.addFlashAttribute("Message", "Required details are missing.");
            return "redirect:/calculation-generation";
        }
    }

    @GetMapping("/salary-slip/{staffId}")
    public String showSalarySlip(@PathVariable("staffId") int staffId, Model model) {
        SalaryDetails salaryDetails = salaryDetailsRepository.findByStaffId(staffId);
        model.addAttribute("salaryDetails", salaryDetails);
        return "salary-slip";
    }

    @GetMapping("/view-salary-details")
    public String viewSalaryDetails(Model model) {
        List<SalaryDetails> salaryDetailsList = salaryDetailsService.getAllSalaryDetails();
        model.addAttribute("salaryDetails", salaryDetailsList);
        return "salary-details"; // This refers to your salary-details.html template
    }

    @GetMapping("/delete-salary/{staffId}")
    public String deleteSalaryDetails(@PathVariable("staffId") Integer staffId, RedirectAttributes redirectAttributes) {
        salaryDetailsService.deleteSalaryDetailsById(staffId);
        redirectAttributes.addFlashAttribute("Message", "Salary details deleted successfully.");
        return "redirect:/view-salary-details";
    }


    @PostMapping("/generate-slip")
    public String generateAndSaveSlip(@RequestParam("staffId") int staffId, @RequestParam("email") String email, RedirectAttributes redirectAttributes, Model model){
        Users user = userRepository.findById(staffId).orElse(null);
        GrossSalary grossSalary = grossSalaryRepository.findByStaffId(staffId);
        DeductionSalary deductionSalary = deductionSalaryRepository.findByStaffId(staffId);

        if (user != null && grossSalary != null && deductionSalary != null){
            SalaryDetails salaryDetails = new SalaryDetails();
            salaryDetails.setStaffId(staffId);
            salaryDetails.setName(user.getName());
            salaryDetails.setDesignation(user.getDesignation());
            salaryDetails.setDeptSection(user.getDept_section());

            salaryDetails.setEBasic(convertToBigDecimal(grossSalary.getBasic()));
            salaryDetails.setEDa(convertToBigDecimal(grossSalary.getDA()));
            salaryDetails.setEHra(convertToBigDecimal(grossSalary.getHRA()));
            salaryDetails.setECca(convertToBigDecimal(grossSalary.getCCA()));
            salaryDetails.setEMa(convertToBigDecimal(grossSalary.getMA()));
            salaryDetails.setECps(convertToBigDecimal(grossSalary.getCPS()));

            salaryDetails.setDCps(convertToBigDecimal(deductionSalary.getCps()));
            salaryDetails.setDGpf(convertToBigDecimal(deductionSalary.getGpf()));
            salaryDetails.setDAupf(convertToBigDecimal(deductionSalary.getAdjustmentUpf()));
            salaryDetails.setDIt(convertToBigDecimal(deductionSalary.getIt()));
            salaryDetails.setDLic(convertToBigDecimal(deductionSalary.getLic()));
            salaryDetails.setDPli(convertToBigDecimal(deductionSalary.getPli()));
            salaryDetails.setDIb(convertToBigDecimal(deductionSalary.getIb()));
            salaryDetails.setDNhis(convertToBigDecimal(deductionSalary.getNhis()));
            salaryDetails.setDAssocn(convertToBigDecimal(deductionSalary.getAssocn()));
            salaryDetails.setDFa(convertToBigDecimal(deductionSalary.getFa()));
            salaryDetails.setDSpf(convertToBigDecimal(deductionSalary.getSpf()));
            salaryDetails.setDFbf(convertToBigDecimal(deductionSalary.getFbf()));
            salaryDetails.setDStaffQuaters(convertToBigDecimal(deductionSalary.getStaffQuarters()));
            salaryDetails.setDSalaryRecovery(convertToBigDecimal(deductionSalary.getSalaryRecovery()));
            salaryDetails.setDCooptex(convertToBigDecimal(deductionSalary.getCoOptex()));
            salaryDetails.setDProfessionalTax(convertToBigDecimal(deductionSalary.getProfessionalTax()));

            BigDecimal grossPay = salaryDetails.getEDa()
                    .add(salaryDetails.getEBasic())
                    .add(salaryDetails.getEHra()).add(salaryDetails.getECca())
                    .add(salaryDetails.getEMa()).add(salaryDetails.getECps());
            salaryDetails.setGrossPay(grossPay);

            BigDecimal totalDeduction = salaryDetails.getDCps().add(salaryDetails.getDGpf())
                    .add(salaryDetails.getDIt()).add(salaryDetails.getDAupf())
                    .add(salaryDetails.getDLic()).add(salaryDetails.getDPli())
                    .add(salaryDetails.getDIb()).add(salaryDetails.getDNhis())
                    .add(salaryDetails.getDAssocn()).add(salaryDetails.getDFa())
                    .add(salaryDetails.getDSpf()).add(salaryDetails.getDStaffQuaters())
                    .add(salaryDetails.getDSalaryRecovery()).add(salaryDetails.getDCooptex())
                    .add(salaryDetails.getDProfessionalTax()).add(salaryDetails.getDFbf());
            salaryDetails.setTotalDeduction(totalDeduction);

            BigDecimal netPay = grossPay.subtract(totalDeduction);
            salaryDetails.setNetPay(netPay);

            salaryDetailsRepository.save(salaryDetails);
            return "redirect:/salary-slip/" + staffId;
        }else{
            redirectAttributes.addFlashAttribute("Message", "Required details are missing.");
            return "redirect:/calculation-generation";
        }
    }

    @PostMapping("/generate-report")
    public String generateReport(@RequestParam("deptSection") String deptSection, Model model) {

        List<SalaryDetails> report = salaryDetailsService.getSalaryReportByDepartment(deptSection);
        BigDecimal totalNetPay = salaryDetailsService.getTotalNetPayByDepartment(deptSection);

        model.addAttribute("report", report);
        model.addAttribute("selectedDepartment", deptSection);
        model.addAttribute("totalNetPay", totalNetPay);

        return "report-view";
    }

    private BigDecimal convertToBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : BigDecimal.ZERO;
    }


}
