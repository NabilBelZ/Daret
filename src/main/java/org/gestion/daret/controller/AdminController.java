package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

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

    @GetMapping("/profile")
    public String profileRedirection(){
        return "profile";
    }

}
