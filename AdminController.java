package com.example.SSGS.Controller;


import com.example.SSGS.EmailService.EmailService;
import com.example.SSGS.Repository.UserRepository;
import com.example.SSGS.Service.AdminService;
import com.example.SSGS.Tables.Users;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@Controller
public class AdminController {

@Autowired
private UserRepository userRepository;

@Autowired
private EmailService emailService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String viewPage(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("loggedInUserName");
        model.addAttribute("name",userName);
        return "admin";
    }

    @GetMapping("/generate-report")
    public String generateRepot() {
        return "generate-report";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/admin/add-users")
    public String addUsers(Model model, @ModelAttribute("successMessage") String successMessage) {
        model.addAttribute("successMessage", successMessage);
        return "add-users";
    }

    @GetMapping("/view-users")
    public String viewUsers(Model model, @ModelAttribute("successMessage") String successMessage){
        List<Users> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("successMessage", successMessage);
        return "view-users";
    }

    @GetMapping("/edit-user/{staff_id}")
    public String showeditUserForm(@PathVariable("staff_id") Integer staff_id, Model model){
        Users user = adminService.getUserById(staff_id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @GetMapping("/calculation-generation")
    public String showCalculationGenerationPage(Model model){
        List<Users> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "calculationGeneration";
    }

    @GetMapping("/send-mail")
    public String showSendMailPage(Model model, HttpSession session){
        String adminEmail = (String) session.getAttribute("emailId");
        String userEmail = (String) session.getAttribute("userEmailId");
        model.addAttribute("adminEmail",adminEmail);
        model.addAttribute("userEmail",userEmail);
        return "send-mail";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute Users u, RedirectAttributes redirectAttributes){
        userRepository.save(u);
        redirectAttributes.addFlashAttribute("successMessage", "User Added successfully!");
        return "redirect:/admin/add-users";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") Users user, RedirectAttributes redirectAttributes){
        adminService.updateUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Record updated successfully!");
        return "redirect:/view-users";
    }

    @PostMapping("/delete-user/{staff_id}")
    public String deleteUser(@PathVariable("staff_id") Integer staff_id, RedirectAttributes redirectAttributes){
        adminService.deleteUserById(staff_id);
        redirectAttributes.addFlashAttribute("successMessage", "Record deleted successfully!");
        return "redirect:/view-users";
    }

    @PostMapping("/send-mail")
    public String sendMail(@RequestParam("fromEmail") String fromEmail,
                           @RequestParam("toEmail") String toEmail,
                           @RequestParam("attachment")MultipartFile attachment,
                           RedirectAttributes redirectAttributes){
        try{
            emailService.sendEmailWithAttachment(fromEmail,toEmail,"Subject: Salary Slip", "Please find the attachment.",attachment);
            redirectAttributes.addFlashAttribute("successMessage", "Email Shared successfully!");
        } catch (SocketTimeoutException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Email Sharing failed: Timeout error - " + e.getMessage());
        } catch (MessagingException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Email Sharing failed: Messaging error - " + e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Email Sharing failed: IO error - " + e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Email Sharing failed:" + e.getMessage());
        }
        return "redirect:/send-mail";
    }
}

