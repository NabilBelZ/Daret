package org.gestion.daret.services.impl;

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

import java.util.ArrayList;

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
        daret.setDateDemarrage(daretDto.getDateDemarrage());
        daret.setDateFin(daretDto.getDateFin());
        daret.setPeriode(daretDto.getPeriode());
        daret.setMontantTotal(daretDto.getMontantTotal());
        daret.setDescription(daretDto.getDescription());
        daret.setEtat(true);
        //daret.setNbParticipant(0);
      //  daret.setTourDeRole(new ArrayList<>());
       // daret.setParticipations(new ArrayList<>());
        daretRepository.save(daret);
        model.addAttribute("msgSuccess", "Felicitation ! Daret Créee avec succes !");
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

}
