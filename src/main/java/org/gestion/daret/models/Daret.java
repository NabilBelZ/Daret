package org.gestion.daret.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.Tour;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Daret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDaret;
    private String nom;
    private String periode;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dateDemarrage;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dateFin;
    private float montant;
    private float montantTotal;
    private boolean etat;
    private int nbParticipant = 0;
    @OneToMany
    private List<Tour> tourDeRole;
    @OneToMany
    private List<Participation> participations;

}
