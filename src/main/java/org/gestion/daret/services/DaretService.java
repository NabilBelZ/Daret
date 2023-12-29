package org.gestion.daret.services;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.DaretDto;
import org.springframework.ui.Model;

import java.util.Date;

public interface DaretService {

    public String createDaret(DaretDto daretDto, Model model);
    public String readDaret(DaretDto DaretDto, Model model);
    public String updateDaret(DaretDto DaretDto, Model model);
    public String deleteDaret(DaretDto DaretDto, Model model);
}
