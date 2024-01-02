package org.gestion.daret.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.Tour;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String periodeTour;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dateDemarrage;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date dateFin;
    private float montant;
    private float montantTotal;
    private String description;
    private boolean etat;
    private int nbParticipant = 0;
    @OneToMany(mappedBy = "daret")// , cascade = CascadeType.ALL   <------ mn ahsan tkon f OneToMany hit la mshna daret ga3 les tours dyalha aytmsho
    private List<Tour> tourDeRole;

    @OneToMany(mappedBy = "daret")
    private List<Participation> participations;
// ADD TIMESTAMP TO ALL THE MODELS (LIKE THE PARTICIPATION CREATION, IF WE WANT TO STOP IT WE HAVE TO ADD THE DURATION OF THE TOUR TO THE PARTICIPATION)
// bnisba l dik createDaret et UserRegistrationProcess mn ahsan tdir chi fonction fl model li tatkhwi fiha dto f lmodel o tb9a t3yt liha f Service
    // fhad TIMESTAMP KAYNA 2 attributs CreatedAt , Updatedat
}
