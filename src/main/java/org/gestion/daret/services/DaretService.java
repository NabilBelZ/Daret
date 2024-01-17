package org.gestion.daret.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.DaretDto;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

public interface DaretService {

    public String createDaret(DaretDto daretDto, RedirectAttributes redirectAttributes);
    public String readDaret(DaretDto DaretDto, Model model);
    public String updateDaret(DaretDto daretDto, RedirectAttributes redirectAttributes);
    public String deleteDaret(int id,  RedirectAttributes redirectAttributes);
    public String listeDarets(Model model);
    public String redirectionAjout(DaretDto daretDto, Model model, HttpSession session);
    public String getInfo2emeForm(Model model, HttpSession session);

    public DaretDto getDaretDetails(int id);
}
