package org.gestion.daret.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.net.http.HttpRequest;

public interface RoleService {
    public String CheckRole(HttpSession session, Model model);

}
