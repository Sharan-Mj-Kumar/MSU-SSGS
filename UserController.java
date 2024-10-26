package com.example.SSGS.Controller;

import com.example.SSGS.Service.SalaryDetailsService;
import com.example.SSGS.Service.UserService;
import com.example.SSGS.Tables.SalaryDetails;
import com.example.SSGS.Tables.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SalaryDetailsService salaryDetailsService;

    @GetMapping("/userpage")
    public String showUserPage(Model model, HttpSession session){
        String userName = (String) session.getAttribute("loggedInUserName");
        model.addAttribute("name",userName);
        return "userpage";
    }

    @GetMapping("/user/logout")
    public String userLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/update-profile")
    public String showUpdateProfileForm(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            throw new RuntimeException("User not found");
        }
        Users user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "update-profile";
    }

    @GetMapping("/my-details")
    public String showMyDetails(Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            throw new RuntimeException("User not found");
        }
        Users user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "my-details";
    }

    @GetMapping("/view-salary-slip")
    public String showSalarySlipForm(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            throw new RuntimeException("User not found");
        }
        Users user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "view-salary-slip";  // Form for selecting salary month
    }


    @PostMapping("/update-profile")
    public String updateUserProfile(@ModelAttribute("user") Users user, RedirectAttributes redirectAttributes){
        if (user.getStaff_id() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        userService.updateUserProfile(user);
        redirectAttributes.addFlashAttribute("successMessage", "Details Updated!");
        return "redirect:/update-profile";
    }

    @PostMapping("/view-salary")
    public String viewSalarySlip(@RequestParam("salaryMonth") String salaryMonth,
                                 HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("loggedInUserId");
        if (userId == null) {
            throw new RuntimeException("User not found");
        }

        Users user = userService.getUserById(userId);

        // Convert the salaryMonth from YYYY-MM to "YYYY-MonthName"
        String[] parts = salaryMonth.split("-");
        String year = parts[0];
        String month = parts[1];

        // Convert numerical month to month name
        String monthName = getMonthName(month);
        String formattedMonth = year + "-" + monthName;

        SalaryDetails salaryDetails = salaryDetailsService
                .findByStaffIdAndSalaryMonth(user.getStaff_id(), formattedMonth);

        if (salaryDetails != null) {
            model.addAttribute("salaryDetails", salaryDetails);
            return "Usalary-slip";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Salary details not found for the selected month.");
            return "redirect:/view-salary-slip";
        }
    }

    private String getMonthName(String month) {
        switch (month) {
            case "01": return "January";
            case "02": return "February";
            case "03": return "March";
            case "04": return "April";
            case "05": return "May";
            case "06": return "June";
            case "07": return "July";
            case "08": return "August";
            case "09": return "September";
            case "10": return "October";
            case "11": return "November";
            case "12": return "December";
            default: throw new IllegalArgumentException("Invalid month: " + month);
        }
    }
}
