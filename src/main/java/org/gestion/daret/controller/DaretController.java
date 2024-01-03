package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.DaretDto;
import org.gestion.daret.dto.UserDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.DaretService;
import org.gestion.daret.services.PasswordService;
import org.gestion.daret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller

public class DaretController {

    @Autowired
    private DaretRepository daretRepository;

    @Autowired
    private DaretService daretService;
    @Autowired
    private UserService userService;

    //@GetMapping("darets")
    //public ResponseEntity<List<Daret>> getDaret(){
    // List<Daret>  darets = new ArrayList<>();
    //darets.add(new Daret(1, "Daret ESTK","1 an", new Date(2023 - 1900, 11, 30), new Date(2024 - 1900,11,30), 1000, 10000, true, 0, new ArrayList<>(), new ArrayList<>()));
    //darets.add(new Daret(2, "Daret Dar","1 an", new Date(2024 - 1900, 0, 2), new Date(2025 - 1900, 0, 2), 9000, 90000, true, 0, new ArrayList<>(), new ArrayList<>()));
    //return ResponseEntity.ok(darets);
    //}
    @PostMapping("/createDaret")
    public String createDaret(@ModelAttribute("daret") DaretDto daretDto, RedirectAttributes redirectAttributes){
        return daretService.createDaret(daretDto, redirectAttributes);
    }

    @PostMapping("/redirectionAjout")
    public String redirectionAjoutDaret(@ModelAttribute("daret") DaretDto daretDto, Model model, HttpSession session){
        return daretService.redirectionAjout(daretDto, model, session);
    }
    @GetMapping("/2emeform")
    public String GetInfoFrom2Daret(Model model, HttpSession session){
        return daretService.getInfo2emeForm(model, session);
    }
    @GetMapping("/ajouterDaret")
    public String CreateDaretPage(){
        return "ajouterDaret";
    }
    @GetMapping("/tontines")
    public String AfficherlisteDarets(Model model) {
        return daretService.listeDarets(model);
    }
    @GetMapping("/deleteDaret/{id}")
    public String SupprimerDaret(@PathVariable("id") int idDaret, RedirectAttributes redirectAttributes){
        return daretService.deleteDaret(idDaret, redirectAttributes);

    }
    @GetMapping("/modifierDaret/{id}")
    public String redirectionModificationDaret(@PathVariable("id") int idDaret, Model model){
        DaretDto daretDto = daretService.getDaretDetails(idDaret);
        model.addAttribute("daret", daretDto);
        return "modifierDaret";
    }
    @PostMapping("/modifierLaDaret")
    public String modificationDaret(DaretDto daretDto, RedirectAttributes redirectAttributes){
        return daretService.updateDaret(daretDto, redirectAttributes);
    }
    @GetMapping("/afficherDaret/{id}")
    public String redirectionAffichageDaret(@PathVariable("id") int idDaret, Model model){
        DaretDto daretDto = daretService.getDaretDetails(idDaret);
        model.addAttribute("daret", daretDto);
        return "afficherDaret";
    }
}

