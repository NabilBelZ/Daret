package org.gestion.daret.controller;

import org.gestion.daret.models.Daret;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/gestionDaret/")
public class DaretController {
    @GetMapping("darets")
    public ResponseEntity<List<Daret>> getDaret(){
       List<Daret>  darets = new ArrayList<>();
       darets.add(new Daret(1, "Daret ESTK","1 an", new Date(2023 - 1900, 11, 30), new Date(2024 - 1900,11,30), 1000, 10000, true, 0, new ArrayList<>(), new ArrayList<>()));
       darets.add(new Daret(2, "Daret Dar","1 an", new Date(2024 - 1900, 0, 2), new Date(2025 - 1900, 0, 2), 9000, 90000, true, 0, new ArrayList<>(), new ArrayList<>()));
       return ResponseEntity.ok(darets);
    }

}
