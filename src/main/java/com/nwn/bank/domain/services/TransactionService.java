package com.nwn.bank.domain.services;

import com.nwn.bank.domain.entities.Transaction;
import com.nwn.bank.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void enregistrerTransaction(Transaction.TypeOperation type, String source, String destination, BigDecimal montant, String description) {
        Transaction transaction = new Transaction();
        transaction.setTypeOperation(type.name());
        transaction.setCompteSource(source);
        transaction.setCompteDestination(destination);
        transaction.setMontant(montant);
        transaction.setDateOperation(LocalDateTime.now());
        transaction.setDescription(description);

        transactionRepository.save(transaction);
    }
}
