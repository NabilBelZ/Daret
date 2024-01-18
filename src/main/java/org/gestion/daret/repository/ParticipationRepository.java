package org.gestion.daret.repository;

import org.gestion.daret.models.Daret;
import org.gestion.daret.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    //Optional<Participation> save();

    List<Participation> findAllByOrderByIdDesc();
    Optional<Participation> findById(int id);
}
