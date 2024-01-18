package org.gestion.daret.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Daret daret;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Membre> membre;
    private float montantParticipation;
    @OneToMany(mappedBy = "participation")
    private List<Tour> tours;
    private int etat;
}
