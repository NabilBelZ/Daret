package org.gestion.daret.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private String periode;
    //@ManyToOne
    //private Membre membre;
    @ManyToOne
    private Daret daret;
    @ManyToOne
    private Participation participation;
   //@ManyToOne
   //private Membre Membre;
}
