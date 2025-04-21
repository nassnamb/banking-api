package com.nwn.bank.domain.services;

import com.nwn.bank.domain.entities.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Service
public class BankService {

    @Autowired
    private CompteBancaireService compteBancaireService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    public void transfererArgent(String source, String destination, double montant) {
        // Vérification préalable
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }

        // Opérations atomiques sous transaction
        compteBancaireService.debiter(source, montant);
        compteBancaireService.crediter(destination, montant);

        transactionService.enregistrerTransaction(
                Transaction.TypeOperation.TRANSFERT,
                source,
                destination,
                BigDecimal.valueOf(montant),
                "Transfert entre comptes"
        );
    }
}