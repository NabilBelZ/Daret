package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.ParticipationRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.RoleService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    DaretRepository daretRepository;

    @Autowired
    ParticipationRepository participationRepository;

    @Autowired
    UserService userService;

    @GetMapping("/adminDashboard")
    public String redirectionAdminDashboard(HttpSession session, Model model) throws Exception {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null && userId != 0) {
            User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
            model.addAttribute("user", user);
        }

        List<Daret> tontines = daretRepository.findAllByEtatIsTrueOrderByIdDesc();
        model.addAttribute("tontines", tontines);

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
        if(user != null){
            userRepository.deleteById(id);
        }
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


    @GetMapping("/editStatusToActive/{id}")
    public String editStatusToActiveProcess(@PathVariable int id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()-> new Exception("user not found !"));
        user.setStatus(true);
        userRepository.save(user);
        return "redirect:/listUsers";
    }

    @GetMapping("/editStatusToSuspended/{id}")
    public String editStatusToSuspendedProcess(@PathVariable int id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()-> new Exception("user not found !"));
        user.setStatus(false);
        userRepository.save(user);
        return "redirect:/listUsers";
    }

    @GetMapping("/ajouterUserLink")
    public String AjouterUserRedirection(Model model) {
        model.addAttribute("user", new User());
        return "ajouterUser";
    }

    @PostMapping("/nouveauUtilisateur")
    public String newUser(@ModelAttribute("user") UserDto userDto, @RequestParam("passConfirmation") String passConfirmation, Model model){
        return userService.nouveauUtilisateurProcess(userDto, passConfirmation, model);
    }

}