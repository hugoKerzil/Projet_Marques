package com.repositories;

import com.entities.Authen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthenRepository extends JpaRepository<Authen, Long> {
    Optional<Authen> findByPseudo(String pseudo);
    boolean existsByPseudo(String pseudo);
}