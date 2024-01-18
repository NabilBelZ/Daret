package org.gestion.daret.services;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface UserService {
    public String RegistrationProcess(UserDto userDto, String passConfirmation, Model model, HttpSession session);
    public String LoginProcess(UserDto userDto, Model model, HttpSession session);
    public String homeRedirection(Model model, HttpSession session);

    public String seDeconnecter(HttpSession session);

    public String nouveauUtilisateurProcess(UserDto userDto, String passConfirmation, Model model);

}
