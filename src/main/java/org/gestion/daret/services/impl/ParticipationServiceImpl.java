package org.gestion.daret.services.impl;

import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.gestion.daret.repository.DaretRepository;
import org.gestion.daret.repository.ParticipationRepository;
import org.gestion.daret.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}