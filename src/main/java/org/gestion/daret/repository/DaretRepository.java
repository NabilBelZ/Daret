package org.gestion.daret.repository;

import org.gestion.daret.models.Daret;
import org.gestion.daret.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DaretRepository extends JpaRepository<Daret, Integer> {
    Optional<Daret> findByIdDaret(int idDaret);
    //Optional<Daret> save();
}
