package org.gestion.daret.dto;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.Tour;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaretDto {
    private int idDaret;
    private String nom;
    private String periode;

    private Date dateDemarrage;

    private Date dateFin;
    private float montant;
    private float montantTotal;

}
