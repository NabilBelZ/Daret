package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.PasswordService;
import org.gestion.daret.services.RoleService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/register")
    public String registrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration_process")
    public String processRegistration(@ModelAttribute("user") UserDto userDto, @RequestParam("passConfirmation") String passConfirmation, Model model, HttpSession session){
        return userService.RegistrationProcess(userDto, passConfirmation, model, session);
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/loginProcess")
    public String processLogin(@ModelAttribute("user") UserDto userDto, Model model, HttpSession session) {
        return userService.LoginProcess(userDto, model, session);
    }

    @GetMapping("/home")
    public String homePage(Model model, HttpSession session){
        return userService.homeRedirection(model, session);
    }


    @GetMapping("/adminDashboard")
    public String redirectionAdminDashboard(HttpSession session){
        return roleService.CheckRole(session);
    }

    @GetMapping("/userDashboard")
    public String redirectionUserDashboard(HttpSession session){
        return roleService.CheckRole(session);
    }

    @GetMapping("/seDeconnecter")
    public String seDeconnecterProcess(HttpSession session){
        return userService.seDeconnecter(session);
    }

}
