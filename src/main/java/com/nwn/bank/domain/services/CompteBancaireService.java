package com.nwn.bank.domain.services;

import com.nwn.bank.domain.entities.CompteBancaire;
import com.nwn.bank.domain.repository.CompteBancaireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class CompteBancaireService {

    @Autowired
    private CompteBancaireRepository compteRepository;

    public List<CompteBancaire> getAll() {
        final List<CompteBancaire> all = compteRepository.findAll();
        return compteRepository.findAll();
    }

    public CompteBancaire getByNumeroCompte (String numero) {
        return compteRepository.findByNumeroCompte(numero)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
    }

    public CompteBancaire getById(Long id) {
        return compteRepository.findById(id).orElse(null);
    }

    @Transactional
    public void crediter(String numeroCompte, double montant) {
        CompteBancaire compte = getByNumeroCompte(numeroCompte);
        if (compte == null) {
            throw new RuntimeException("Compte introuvable: " + numeroCompte);
        }
        compte.setSolde(compte.getSolde() + montant);
        compteRepository.save(compte);
    }

    @Transactional
    public void debiter(String numeroCompte, double montant) {
        CompteBancaire compte = getByNumeroCompte(numeroCompte);
        if (compte == null) {
            throw new RuntimeException("Compte introuvable: " + numeroCompte);
        }
        if (compte.getSolde() < montant) {
            throw new RuntimeException("Solde insuffisant pour le compte: " + numeroCompte);
        }
        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);
    }

    public double getSolde(String numeroCompte) {
        CompteBancaire compte = getByNumeroCompte(numeroCompte);
        if (compte == null) {
            throw new RuntimeException("Compte introuvable: " + numeroCompte);
        }
        return compte.getSolde();
    }
}