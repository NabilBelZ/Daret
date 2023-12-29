package org.gestion.daret.services.impl;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.services.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.http.HttpRequest;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public String CheckRole(HttpSession session, Model model){

        String role = (String) session.getAttribute("role");

        if (role != null && role.equals("admin")) {
            return "page-restreinte";
        } else {
            model.addAttribute("message", "Accès interdit. Vous n'avez pas les autorisations nécessaires.");
            return "erreur";
        }
    }
}
