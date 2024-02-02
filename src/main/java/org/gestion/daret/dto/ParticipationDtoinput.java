package org.gestion.daret.dto;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Membre;
import org.gestion.daret.models.Tour;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationDtoinput {

    private int id;
    private float montantParticipation;


}
