package org.gestion.daret.models;

import jakarta.persistence.*;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "user";

    @Column(nullable = false)
    private Boolean status = true;

    @OneToMany(mappedBy = "user") // bhal hnaya khdama dak cascade hit ila mshna user aytmsho les membre
   private List<Membre> membres;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Participation> participation;

    private double solde;
}