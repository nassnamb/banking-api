package com.nwn.bank.api.controllers;

import com.nwn.bank.domain.entities.Transaction;
import com.nwn.bank.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/{numeroCompte}")
    public List<Transaction> getHistoriqueCompte(@PathVariable String numeroCompte) {
        return transactionRepository.findByCompteSourceOrCompteDestinationOrderByDateOperationDesc(
                numeroCompte,
                numeroCompte
        );
    }
}
