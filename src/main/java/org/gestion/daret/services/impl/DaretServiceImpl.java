package org.gestion.daret.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.DaretDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.DaretService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.http.HttpRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class DaretServiceImpl implements DaretService {

    private final DaretRepository daretRepository;

    @Autowired
    public DaretServiceImpl(DaretRepository daretRepository) {
        this.daretRepository = daretRepository;
    }

    @Override
    public String createDaret(DaretDto daretDto, Model model) {
        Daret daret = new Daret();
        daret.setIdDaret(daretDto.getIdDaret());
        daret.setNom(daretDto.getNom());
        daret.setMontant(daretDto.getMontant());
        //daret.setDateDemarrage(daretDto.getDateDemarrage());
        //daret.setDateFin(daretDto.getDateFin());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateDemarrage = dateFormat.parse(daretDto.getDateDemarrage());
            Date dateFin = dateFormat.parse(daretDto.getDateFin());

            // Set the Date objects in the Daret entity
            daret.setDateDemarrage(dateDemarrage);
            daret.setDateFin(dateFin);
        } catch (ParseException e) {
            // Handle parsing exception if needed
            e.printStackTrace();
            }
        daret.setPeriode(daretDto.getPeriode());
        daret.setMontantTotal(daretDto.getMontantTotal());
        String cleanedDescription = daretDto.getDescription().replaceAll("<p>", "").replaceAll("</p>", "");
        daret.setDescription(cleanedDescription);
        daret.setEtat(true);
        //daret.setNbParticipant(0);
      //  daret.setTourDeRole(new ArrayList<>());
       // daret.setParticipations(new ArrayList<>());
        daretRepository.save(daret);
        model.addAttribute("msgSuccess", "Félicitations ! La Daret a été créée avec succès !");
        return "adminDashboard";
    }

    @Override
    public String readDaret(DaretDto DaretDto, Model model) {
        return null;
    }

    @Override
    public String updateDaret(DaretDto DaretDto, Model model) {
        return null;
    }

    @Override
    public String deleteDaret(DaretDto DaretDto, Model model) {
        return null;
    }

    @Override
    public String redirectionAjout(DaretDto daretDto, Model model, HttpSession session) {
        session.setAttribute("nom", daretDto.getNom());
        session.setAttribute("description", daretDto.getDescription());
        return "redirect:/2emeform";
    }
    @Override
    public String getInfo2emeForm(Model model, HttpSession session) {
        //Object nom = request.getAttribute("nom");
        //Object description = request.getAttribute("description");
        String nom = (String) session.getAttribute("nom");
        String description = (String) session.getAttribute("description");
        model.addAttribute("nom", nom);
        model.addAttribute("description", description);

        return "ajouterDaret";
    }
}
