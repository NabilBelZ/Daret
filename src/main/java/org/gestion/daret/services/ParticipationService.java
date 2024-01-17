package org.gestion.daret.services;

import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public interface ParticipationService {
    void saveParticipation(Participation participation);
}