package org.gestion.daret.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationDto {
    private int id;
    private int idDaret;
    private String nomDaret;
    private float montantDaret;
    private String nomUser;
    private String prenomUser;
    private float montantParticipation;
    private int etat;
    private int tour;
    private float montantTotalDaret;
}
