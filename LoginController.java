package com.example.SSGS.Controller;

import com.example.SSGS.Tables.Users;
import com.example.SSGS.Service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String home() {
        return "home";  // This maps to home.html in the resources/templates folder
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){
        Users user = adminService.authenticateUser(email, password);

        if(user != null){
            session.setAttribute("loggedInUserId", user.getStaff_id());
            session.setAttribute("loggedInUserName", user.getName());
            session.setAttribute("emailId", user.getEmail());

            if(user.getIs_admin() == Boolean.TRUE){
                return "redirect:/admin";
            }else{
                return "redirect:/userpage";
            }

        }else{
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

    }
}
