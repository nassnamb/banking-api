package com.nwn.bank.domain.repository;

import com.nwn.bank.domain.entities.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {

    Optional<CompteBancaire> findByNumeroCompte(String numeroCompte);
}
