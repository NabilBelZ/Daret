package org.gestion.daret.controller;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.MesParticipationDto;
import org.gestion.daret.dto.ParticipationDto;
import org.gestion.daret.dto.ParticipationDtoinput;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.Tour;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.ParticipationRepository;
import org.gestion.daret.repository.UserRepository;
import org.gestion.daret.services.ParticipationService;
import org.gestion.daret.services.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class ParticipationController {

    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private UserService userService;


    @Autowired
    private DaretRepository daretRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ParticipationController(ParticipationRepository participationRepository, DaretRepository daretRepository){
        this.participationRepository = participationRepository;
        this.daretRepository = daretRepository;
    }

    @GetMapping("/accepterDemande/{id}/{idDaret}")
    public String accepterDemande(@PathVariable("id") int id, @PathVariable("idDaret") int id_daret, RedirectAttributes redirectAttributes){
        return participationService.accepterDemande(id, id_daret, redirectAttributes);
    }
    @GetMapping("/accepterDemande2/{id}/{idDaret}")

    public String accepterDemande2(@PathVariable("id") int id_participation, @PathVariable("idDaret") int id_daret, RedirectAttributes redirectAttributes){
        return participationService.accepterDemande2(id_participation, id_daret, redirectAttributes);
    }

    @GetMapping("/refuserDemande/{id}/{idDaret}")
    public String refuserDemande(@PathVariable("id") int id, @PathVariable("idDaret") int id_daret, RedirectAttributes redirectAttributes){
        return participationService.refuserDemande(id, id_daret, redirectAttributes);

    }
    @GetMapping("/refuserDemande2/{id}/{idDaret}")
    public String refuserDemande2(@PathVariable("id") int id, @PathVariable("idDaret") int id_daret, RedirectAttributes redirectAttributes){
        return participationService.refuserDemande2(id, id_daret, redirectAttributes);
    }

    @GetMapping("/mettreEnAttenteDemande/{id}/{idDaret}")
    public String mettreEnAttenteLaDemande(@PathVariable("id") int id, @PathVariable("idDaret") int id_daret, RedirectAttributes redirectAttributes){
        return participationService.mettreEnAttenteDemande(id, id_daret, redirectAttributes);
    }

    @GetMapping("/mettreEnAttenteDemande2/{id}/{idDaret}")
    public String mettreEnAttenteLaDemande2(@PathVariable("id") int id, @PathVariable("idDaret") int id_daret, RedirectAttributes redirectAttributes){
        return participationService.mettreEnAttenteDemande2(id, id_daret, redirectAttributes);
    }

    @GetMapping("/supprimerDemande/{id}")
    public String deleteDemande(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        return participationService.supprimerDemande(id, redirectAttributes);
    }

    @GetMapping("/supprimerDemande2/{id}/{idDaret}")
    public String deleteDemande(@PathVariable("id") int id, @PathVariable("idDaret") int idDaret, RedirectAttributes redirectAttributes){
        return participationService.supprimerDemande2(id, idDaret, redirectAttributes);
    }

    // participer version safe
    @PostMapping("/participer")
    public String participer(HttpSession session, @ModelAttribute("participation") ParticipationDtoinput participation,
                             RedirectAttributes redirectAttributes) throws Exception{

        Participation newparticipation = new Participation();
        newparticipation.setEtat(0);

        Optional<Daret> daret = daretRepository.findById(participation.getId());
        if (daret.isPresent()) {
            Participation p = new Participation();
            p.setMontantParticipation(participation.getMontantParticipation());
            p.setDaret(daret.get());
            p.setEtat(0);
            p.setTour(0);
            User user = userRepository.findById((Integer) session.getAttribute("userId")).orElseThrow(()-> new Exception("user not found!"));
            p.setUser(user);
            participationRepository.save(p);
            redirectAttributes.addFlashAttribute("msgsuccess", "Votre demande a été envoyée !");
        }
        return "redirect:/listeTontines";
    }

    // solde check added
    /*@PostMapping("/participer")
    public String participer(HttpSession session, @ModelAttribute("participation") ParticipationDtoinput participation,
                             RedirectAttributes redirectAttributes) throws Exception{

        User user = userRepository.findById((Integer) session.getAttribute("userId")).orElseThrow(()-> new Exception("user not found!"));

        if(user.getSolde() >= participation.getMontantParticipation()){
            Participation newparticipation = new Participation();
            newparticipation.setEtat(0);

            Optional<Daret> daret = daretRepository.findById(participation.getId());
            if (daret.isPresent()) {
                Participation p = new Participation();
                p.setMontantParticipation(participation.getMontantParticipation());
                p.setDaret(daret.get());
                p.setEtat(0);
                p.setUser(user);
                participationRepository.save(p);
                redirectAttributes.addFlashAttribute("msgsuccess", "Votre demande a été envoyée !");
            }
            return "redirect:/listeTontines";
        }else{
            redirectAttributes.addFlashAttribute("msgSoldeInsuffisant", "Solde insuffisant ! Veuillez penser à recharger votre compte.");
        }


    }*/

    @GetMapping("/membreDaret/{id_daret}")
    public String afficherMemebreDaret(@PathVariable("id_daret") int id_daret, Model model) throws Exception{
        List<ParticipationDto> participations = participationService.getMembreDaret(id_daret);
        model.addAttribute("participations", participations);
        //besoin d'id daret pour le traitement de génération
        model.addAttribute("idDaret",id_daret);
        float sommeCotisation = 0;
        boolean tourEmpty = false;
        for(ParticipationDto participationDto : participations){
            if(participationDto.getEtat() == 1){
                sommeCotisation += participationDto.getMontantParticipation();

                if (participationDto.getTour() == 0) {
                    tourEmpty = true;
                    model.addAttribute("TourEmpty", tourEmpty);
                }
            }
        }
        model.addAttribute("sommeCotisation", sommeCotisation);
        return "membreDaret";
    }

    @GetMapping("/listDemandesParticipation")
    public String listeDesDemandesParticipations(Model model){
        List<ParticipationDto> participationDtoList = participationService.getAllParticipations();
        model.addAttribute("participations", participationDtoList);
        return "listDemandesParticipation";
    }

    @GetMapping("/mesParticipations")
    public String getMesParticipations(HttpSession session, Model model) {
        int userId = (int) session.getAttribute("userId");
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            List<MesParticipationDto> mesParticipations = participationService.getMesParticipations(userId);
            if (mesParticipations.isEmpty()) {
                model.addAttribute("message", "Vous n'avez pris part à aucune daret.");
            } else {
                model.addAttribute("mesParticipations", mesParticipations);
            }
        }
        return "mesParticipations";
    }

    @GetMapping("/genererTours/{id_daret}")
    public String genererTour(@PathVariable int id_daret, Model model) {

        List<ParticipationDto> participations = participationService.getMembreDaret(id_daret);
        List<ParticipationDto> listParticipantValides = new ArrayList<>();

        boolean TourEmpty = false;

        for (ParticipationDto participationDto : participations) {
            if (participationDto.getEtat() == 1) {
                listParticipantValides.add(participationDto);
            }
        }

        int nbParticipantsValides = listParticipantValides.size();
        Random random = new Random();

        for (ParticipationDto participationDto : listParticipantValides) {
            int tour;
            boolean tourExists;
            do {
                tour = random.nextInt(nbParticipantsValides) + 1;
                tourExists = false;
                for (ParticipationDto otherParticipant : listParticipantValides) {
                    if (otherParticipant.getTour() == tour && otherParticipant != participationDto) {
                        tourExists = true;
                        break;
                    }
                }
            } while (tourExists);

            participationDto.setTour(tour);

            Optional<Participation> optionalParticipation = participationRepository.findById(participationDto.getId());
            if (optionalParticipation.isPresent()) {
                Participation participation = optionalParticipation.get();
                participation.setTour(participationDto.getTour());
                participationRepository.save(participation);
            }
        }

        // Ajouter les participations non valides à la liste
        List<ParticipationDto> listParticipantNonValides = new ArrayList<>();
        for (ParticipationDto participationDto : participations) {
            if (participationDto.getEtat() != 1) {
                listParticipantNonValides.add(participationDto);
            }
        }

        listParticipantValides.addAll(listParticipantNonValides);

        // Mettre à jour le modèle avec la liste mise à jour
        model.addAttribute("participations", listParticipantValides);

        // Calculer la somme des cotisations
        float sommeCotisation = 0;
        for (ParticipationDto participationDto : listParticipantValides) {
            if (participationDto.getEtat() == 1) {
                sommeCotisation += participationDto.getMontantParticipation();
            }
        }

        model.addAttribute("sommeCotisation", sommeCotisation);



        // Retourner la vue
        return "membreDaret";
    }

}





