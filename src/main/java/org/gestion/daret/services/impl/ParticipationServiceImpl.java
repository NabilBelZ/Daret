package org.gestion.daret.services.impl;

import org.gestion.daret.dto.ParticipationDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.User;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.ParticipationRepository;
import org.gestion.daret.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final DaretRepository daretRepository;

    @Autowired
    public ParticipationServiceImpl(ParticipationRepository participationRepository, DaretRepository daretRepository) {
        this.participationRepository = participationRepository;
        this.daretRepository = daretRepository;
    }

    @Override
    public void saveParticipation(Participation participation) {
        participationRepository.save(participation);
    }
    @Override
    public List<ParticipationDto> getAllParticipations() {
        List<Participation> participations = participationRepository.findAllByOrderByIdDesc();
        return convertToDtoList(participations);
    }

    private List<ParticipationDto> convertToDtoList(List<Participation> participations) {
        List<ParticipationDto> participationDtoList = new ArrayList<>();
        for (Participation participation : participations) {
            ParticipationDto participationDto = new ParticipationDto();
            Daret daret = participation.getDaret();
            participationDto.setId(participation.getId());
            participationDto.setNomDaret(daret.getNom());
            User user = participation.getUser();
            participationDto.setNomUser(user.getLastname());
            participationDto.setPrenomUser(user.getFirstname());
            participationDto.setMontantDaret(daret.getMontant());
            participationDto.setMontantParticipation(participation.getMontantParticipation());
            participationDto.setEtat(participation.getEtat());
            participationDtoList.add(participationDto);
        }
        return participationDtoList;
    }
    @Override
    public String refuserDemande(int id ,RedirectAttributes redirectAttributes) {

        Optional<Participation> optionalParticipation = participationRepository.findById(id);
        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 0 || participation.getEtat() == 1) {
                participation.setEtat(-1);
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgRefus", "La Demande a été refusée !");
            } else if (participation.getEtat() == -1) {
                redirectAttributes.addFlashAttribute("msgRefus", "La Demande a été déjà refusée avant !");
            }
        });
        return "redirect:/listDemandesParticipation";
    }
    @Override
    public String accepterDemande(int id, RedirectAttributes redirectAttributes) {
        Optional<Participation> optionalParticipation = participationRepository.findById(id);

        optionalParticipation.ifPresent(participation -> {
            if (participation.getEtat() == 0 || participation.getEtat() == -1) {
                participation.setEtat(1);
                participationRepository.save(participation);
                redirectAttributes.addFlashAttribute("msgAccept", "La Demande a été acceptée !");
            } else if (participation.getEtat() == 1) {
                redirectAttributes.addFlashAttribute("msgAccept", "La Demande a été déjà acceptée avant !");
            }
        });

        return "redirect:/listDemandesParticipation";
    }

}