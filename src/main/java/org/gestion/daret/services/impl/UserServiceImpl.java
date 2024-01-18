package org.gestion.daret.services.impl;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.PasswordService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public String RegistrationProcess(UserDto userDto, String passConfirmation, Model model, HttpSession session) {
        if (userDto.getPassword().equals(passConfirmation)) {
            User user = new User();
            user.setId(userDto.getId());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordService.hashPassword(userDto.getPassword()));
            userRepository.save(user);
            session.setAttribute("role", userDto.getRole());
            model.addAttribute("msgSuccess", "Utilisateur créé avec succès. !");
            return "login";
        } else {
            model.addAttribute("error", "The entered confirmation password does not match the provided password.");
            return "register";
        }
    }

    @Override
    public String nouveauUtilisateurProcess(UserDto userDto, String passConfirmation, Model model) {
        if (userDto.getPassword().equals(passConfirmation)) {
            User user = new User();
            user.setId(userDto.getId());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordService.hashPassword(userDto.getPassword()));
            userRepository.save(user);
            model.addAttribute("msgSuccess", "Utilisateur créé avec succès. !");
            return "listUsers";
        } else {
            model.addAttribute("error", "The entered confirmation password does not match the provided password.");
            return "ajouterUser";
        }
    }

    @Override
    public String LoginProcess(UserDto userDto, Model model, HttpSession session) {
        try {
            User storedUser = userRepository.findByEmail(userDto.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            if (passwordService.verifyPassword(userDto.getPassword(), storedUser.getPassword())) {
                session.setAttribute("userId", storedUser.getId());
                if(storedUser.getRole().equals("admin")){
                    session.setAttribute("role", storedUser.getRole());
                    session.setAttribute("email", storedUser.getEmail());
                    return "redirect:/adminDashboard";
                }else{
                    session.setAttribute("role", storedUser.getRole());
                    session.setAttribute("email", storedUser.getEmail());
                    return "redirect:/userDashboard";
                }
            } else {
                model.addAttribute("connectionError", "Email or password invalid");
                return "login";

            }
        } catch (NoSuchElementException ex) {
            model.addAttribute("connectionError", "Email or password invalid");
            return "login";
        }
    }

    @Override
    public String homeRedirection(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if(userId != null){
            User storedUser = userRepository.findById(userId).orElse(null);
            model.addAttribute("user", storedUser);
            return "home";
        }else{
            return "redirect:/login";
        }
    }

    public String seDeconnecter(HttpSession session){
        session.invalidate();
        return "login";
    }

}
