package org.gestion.daret.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.Tour;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Membre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMembre;
    @OneToOne
    private User user;

  // private Daret daret;
    @OneToMany
    private List<Tour> tourDeRole;
    @OneToMany
    private List<Participation> participations;
}
