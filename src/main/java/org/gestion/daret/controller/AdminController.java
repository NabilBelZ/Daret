package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.RoleService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    DaretRepository daretRepository;

    @GetMapping("/adminDashboard")
    public String redirectionAdminDashboard(HttpSession session, Model model) throws Exception{
        List<Daret> tontines = daretRepository.findAllByEtatIsTrueOrderByIdDesc();
        model.addAttribute("tontines", tontines);
        int userId = (Integer) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found"));
        model.addAttribute("user", user);
        return roleService.CheckRole(session);
    }

    @GetMapping("/listUsers")
    public String afficherUsers(HttpSession session, Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        String email =  (String) session.getAttribute("email");
        model.addAttribute("session_email", email);
        return "listUsers";
    }

    @GetMapping("/deleteUser/{id}")
    public String DeleteUserProcess(@PathVariable int id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()-> new Exception("user not found"));
        userRepository.deleteById(id);
        return "redirect:/listUsers";
    }

    @GetMapping("/editRoleToUser/{id}")
    public String editRoleToUserProcess(@PathVariable int id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()-> new Exception("user not found !"));
        user.setRole("user");
        userRepository.save(user);
        return "redirect:/listUsers";
    }

    @GetMapping("/editRoleToAdmin/{id}")
    public String editRoleToAdminProcess(@PathVariable int id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()-> new Exception("user not found !"));
        user.setRole("admin");
        userRepository.save(user);
        return "redirect:/listUsers";
    }





}
