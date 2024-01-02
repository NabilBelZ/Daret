package org.gestion.daret.repository;

import org.gestion.daret.models.Daret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DaretRepository extends JpaRepository<Daret, Integer> {
    Optional<Daret> findByIdDaret(int idDaret);


    List<Daret> findAllByEtatIsTrueOrderByIdDaretDesc();



//Optional<Daret> save();
}
