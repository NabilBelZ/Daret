package org.gestion.daret.dto;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gestion.daret.models.Participation;
import org.gestion.daret.models.Tour;

import java.util.Date;
import java.util.List;
// khas mn ahsan wahed DTO li kay getti w wahed li kay affiche
// en tout cas khas bzf dyal DTO koul wahed dyalach koul mra anhtajo wahed houwa li anst3mlo
// mtln pour registration User rah walabouda anhtaj jami3 les infos dyalo wlk la bghit ghe ntconnecta rah anhtaj wahed DTo li anht fiih ghe MDP et email
// bghit matalan n affiche ghe smya dyal user et role rah ahet feh ghe dakchi li bghit
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
    private String description;
    private boolean etat;
}
