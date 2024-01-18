package org.gestion.daret.services;

import org.gestion.daret.dto.ParticipationDto;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public interface ParticipationService {

    void saveParticipation(Participation participation);

    List<ParticipationDto> getAllParticipations();

    public String refuserDemande(int id, RedirectAttributes redirectAttributes);

    public String accepterDemande(int id, RedirectAttributes redirectAttributes);

    public String mettreEnAttenteDemande(int id, RedirectAttributes redirectAttributes);

}