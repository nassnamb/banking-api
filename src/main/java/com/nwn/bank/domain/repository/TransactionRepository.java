package com.nwn.bank.domain.repository;

import com.nwn.bank.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCompteSourceOrCompteDestinationOrderByDateOperationDesc(String compteSource, String compteDestination);
}