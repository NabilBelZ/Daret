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
    private int idParticipation;
    @OneToOne
    private Daret daret;
    @OneToMany
    private List<Membre> membre;
    private float montantParticipation;
    @OneToMany
    private List<Tour> tours;
}
