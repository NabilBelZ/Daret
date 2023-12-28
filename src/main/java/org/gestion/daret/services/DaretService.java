package org.gestion.daret.services;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.DaretDto;
import org.springframework.ui.Model;

import java.util.Date;

public interface DaretService {

    public String createDaret(DaretDto daretDto, Model model);
    public String readDaret(DaretDto DaretDto, Model model, HttpSession session);
    public String updateDaret(Model model, HttpSession session);
}
