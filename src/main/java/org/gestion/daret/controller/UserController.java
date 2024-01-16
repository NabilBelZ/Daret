package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.DaretService;
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

import java.util.List;

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
    public String redirectionUserDashboard(HttpSession session){
        return roleService.CheckRole(session);
    }

    @GetMapping("/seDeconnecter")
    public String seDeconnecterProcess(HttpSession session){
        return userService.seDeconnecter(session);
    }

    @GetMapping("/listeTontines")
    public String listeDesTontines(HttpSession session, Model model){
        List<Daret> tontines = daretRepository.findAll();
        model.addAttribute("tontines", tontines);
        return "listeTontines";
    }

    @GetMapping("/tontineDetails")
    public String DetailsTontine(HttpSession session){
        return "tontineDetails";
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
    public String editPassword(HttpSession session, @ModelAttribute("user") UserDto userDto, Model model,@RequestParam String oldPassword)
            throws Exception{
        int userId = (Integer) session.getAttribute("userId");

        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("user not found"));

        if(passwordService.verifyPassword(oldPassword, user.getPassword())){
            user.setPassword(passwordService.hashPassword(userDto.getPassword()));
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
        return "profile";
    }



}
