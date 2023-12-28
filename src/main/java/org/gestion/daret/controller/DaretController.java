package org.gestion.daret.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/gestionDaret/")
public class DaretController {

    @Autowired
    private DaretRepository daretRepository;

    @Autowired
    private DaretService daretService;
    @GetMapping("darets")
    public ResponseEntity<List<Daret>> getDaret(){
       List<Daret>  darets = new ArrayList<>();
       darets.add(new Daret(1, "Daret ESTK","1 an", new Date(2023 - 1900, 11, 30), new Date(2024 - 1900,11,30), 1000, 10000, true, 0, new ArrayList<>(), new ArrayList<>()));
       darets.add(new Daret(2, "Daret Dar","1 an", new Date(2024 - 1900, 0, 2), new Date(2025 - 1900, 0, 2), 9000, 90000, true, 0, new ArrayList<>(), new ArrayList<>()));
       return ResponseEntity.ok(darets);
    }
    @PostMapping("/createDaret")
    public String createDaret(@ModelAttribute("daret") DaretDto daretDto, Model model){
        return daretService.createDaret(daretDto, model);
    }
}
