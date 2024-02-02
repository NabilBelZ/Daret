package org.gestion.daret.services;

import jakarta.servlet.http.HttpSession;
import org.gestion.daret.dto.MesParticipationDto;
import org.gestion.daret.dto.ParticipationDto;
import org.gestion.daret.models.Participation;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public interface ParticipationService {

    void saveParticipation(Participation participation);

    List<ParticipationDto> getAllParticipations();

    String refuserDemande(int id, int id_daret, RedirectAttributes redirectAttributes);

    public String refuserDemande2(int id, int id_daret, RedirectAttributes redirectAttributes);

    public String accepterDemande(HttpSession session, int id, int id_daret, RedirectAttributes redirectAttributes);

    public String accepterDemande2(int id, int id_daret, RedirectAttributes redirectAttributes);

    public String mettreEnAttenteDemande(int id, int id_daret, RedirectAttributes redirectAttributes);

    public String mettreEnAttenteDemande2(int id, int id_daret, RedirectAttributes redirectAttributes);

    public String supprimerDemande(int id, RedirectAttributes redirectAttributes);

    public String supprimerDemande2(int id, int id_daret, RedirectAttributes redirectAttributes);

    public List<ParticipationDto> getMembreDaret(int id);

    List<MesParticipationDto> getMesParticipations(int userId);

    public List<ParticipationDto> convertToDtoList(List<Participation> participations);


}