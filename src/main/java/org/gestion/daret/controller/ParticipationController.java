package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.ParticipationDtoinput;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.ParticipationRepository;
import org.gestion.daret.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Controller
public class ParticipationController {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private DaretRepository daretRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ParticipationController(ParticipationRepository participationRepository, DaretRepository daretRepository){
        this.participationRepository = participationRepository;
        this.daretRepository = daretRepository;
    }

    @PostMapping("/participer")
    public String participer(HttpSession session, @ModelAttribute("participation") ParticipationDtoinput participation,
                             RedirectAttributes redirectAttributes) throws Exception{

        Participation newparticipation = new Participation();
        newparticipation.setEtat(false);

        Optional<Daret> daret = daretRepository.findById(participation.getId());
        if (daret.isPresent()) {
            Participation p = new Participation();
            p.setMontantParticipation(participation.getMontantParticipation());
            p.setDaret(daret.get());
            p.setEtat(false);
            User user = userRepository.findById((Integer) session.getAttribute("userId")).orElseThrow(()-> new Exception("user not found!"));
            p.setUser(user);
            participationRepository.save(p);
            redirectAttributes.addFlashAttribute("msgsuccess", "Votre demande a été envoyée !");
        }
        return "redirect:/listeTontines";
    }
    @GetMapping("/listDemandes")
        public String listeDesDemandesParticipations(Model model) throws Exception{
        List<Daret> tontines = daretRepository.findAll();
        List<User> users = userRepository.findAll();

        model.addAttribute("tontines", tontines);
        model.addAttribute("users", users);

        return "listDemandes";
    }

}
