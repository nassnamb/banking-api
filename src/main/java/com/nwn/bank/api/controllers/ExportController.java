package com.nwn.bank.api.controllers;

import com.nwn.bank.domain.entities.Transaction;
import com.nwn.bank.domain.repository.TransactionRepository;
import com.nwn.bank.domain.services.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank/export")
@CrossOrigin(origins = "http://localhost:3000")
public class ExportController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ExportService exportService;

    @GetMapping("/pdf/{accountNumber}")
    public ResponseEntity<ByteArrayResource> exportToPdf(@PathVariable String accountNumber) throws Exception {
        List<Transaction> transactions = transactionRepository
                .findByCompteSourceOrCompteDestinationOrderByDateOperationDesc(accountNumber, accountNumber);

        byte[] pdfBytes = exportService.generateTransactionsPdf(transactions, accountNumber);

        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=transactions_" + accountNumber + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(resource);
    }

    @GetMapping("/csv/{accountNumber}")
    public ResponseEntity<ByteArrayResource> exportToCsv(@PathVariable String accountNumber) throws Exception {
        List<Transaction> transactions = transactionRepository
                .findByCompteSourceOrCompteDestinationOrderByDateOperationDesc(accountNumber, accountNumber);

        byte[] csvBytes = exportService.generateTransactionsCsv(transactions);

        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=transactions_" + accountNumber + ".csv")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(csvBytes.length)
                .body(resource);
    }
}