package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.MesParticipationDto;
import org.gestion.daret.dto.ParticipationDto;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.ParticipationService;
import org.gestion.daret.services.PasswordService;
import org.gestion.daret.services.RoleService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DaretRepository daretRepository;

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


    @GetMapping("/userDashboard")
    public String redirectionUserDashboard(HttpSession session, Model model) throws Exception{
        Integer userId = (Integer) session.getAttribute("userId");
        if(userId != null && userId != 0){
            User user = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found!"));
            model.addAttribute("user", user);
            List<MesParticipationDto> mesParticipations = participationService.getMesParticipations(userId);

            model.addAttribute("mesParticipations", mesParticipations);
        }
        return roleService.CheckRole(session);
    }

    @GetMapping("/seDeconnecter")
    public String seDeconnecterProcess(HttpSession session){
        return userService.seDeconnecter(session);
    }


    @PostMapping("/modifierInfoUser_process")
    public String modifierInfoUser(HttpSession session, @ModelAttribute("user") UserDto userDto) throws Exception{
        int userId = (Integer) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found"));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        if(user.getEmail().equals(userDto.getEmail())){
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
            return "redirect:/profile";
        }else{
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
            return "redirect:/seDeconnecter";
        }
    }

    @PostMapping("/modifierMotdePasse_process")
    public String editPassword(HttpSession session, Model model,@RequestParam(name="oldPassword") String oldPassword,
                               @RequestParam(name = "newPassword") String newPassword)
            throws Exception{
        int userId = (Integer) session.getAttribute("userId");

        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found"));

        if(passwordService.verifyPassword(oldPassword, user.getPassword())){
            user.setLastname(user.getLastname());
            user.setFirstname(user.getFirstname());
            user.setRole(user.getRole());
            user.setStatus(user.getStatus());
            user.setPassword(passwordService.hashPassword(newPassword));
            userRepository.save(user);
            return "redirect:/seDeconnecter";
        }
        else{
            model.addAttribute("password_error", true);
            return "profile";
        }
    }

    @GetMapping("/profile")
    public String profileRedirection(HttpSession session, Model model) throws Exception{
        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email).orElseThrow(()-> new Exception("user not found"));
        model.addAttribute("user", user);
        int userId = (int) session.getAttribute("userId");

        List<MesParticipationDto> mesParticipations = participationService.getMesParticipations(userId);

        model.addAttribute("mesParticipations", mesParticipations);
        return "profile";
    }


    @PostMapping("/ajouterSoldeProcess")
    public String processForm(HttpSession session, @RequestParam("amount") double amount, Model model, RedirectAttributes redirectAttributes) throws Exception{
        int userId = (Integer) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found"));

        double nvSolde = user.getSolde() + amount;
        user.setSolde(nvSolde);
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("msgAjoutMontant","Votre solde a été augmenté avec succès");

        return "redirect:/userDashboard";
    }
}