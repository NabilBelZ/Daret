package org.gestion.daret.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesParticipationDto {
    private String nomDaret;
    private float montantDaret;
    private float montantDaretTotal;
    private Date dateDemarrage;
    private Date dateFin;
    private String periodeDaret;
    private String periodeTour;
    private int nbParticipant;
    private String descriptionDaret;
    private float montantParticipation;
}
