package org.gestion.daret.controller;

import org.gestion.daret.dto.ParticipationDtoinput;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ParticipationController {


    private ParticipationRepository participationRepository;

    private DaretRepository daretRepository;
@Autowired
ParticipationController(ParticipationRepository participationRepository, DaretRepository daretRepository){
    this.participationRepository = participationRepository;
    this.daretRepository = daretRepository;
}
    @PostMapping("/participer")
    public String participer(@ModelAttribute("participation") ParticipationDtoinput participation, RedirectAttributes redirectAttributes) {

        Participation newparticipation = new Participation();
        newparticipation.setEtat(false);

        //if (optionalDaret.isPresent()) {

        //} else {
        //    System.out.println("DARET NOT FOUND WITH ID" + id);

       // }
        Optional<Daret> daret = daretRepository.findById(participation.getId());
        if (daret.isPresent()) {
            Participation p = new Participation();
            p.setMontantParticipation(participation.getMontantParticipation());
            p.setDaret(daret.get());
            p.setEtat(false);


            participationRepository.save(p);
            redirectAttributes.addFlashAttribute("msgsuccess", "Votre demande a été envoyée !");
        }

        return "redirect:/listeTontines";
    }

}
