package com.nwn.bank.api.controllers;

import com.nwn.bank.domain.entities.CompteBancaire;
import com.nwn.bank.domain.services.BankService;
import com.nwn.bank.domain.services.CompteBancaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/bank")
public class BankingController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BankService bankService;

    @Autowired
    private CompteBancaireService compteBancaireService;

    public BankingController(BankService bankService) {
        this.bankService = bankService;
    }

    @Operation(summary = "Money Transfer", description = "transfer money from account to account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transfer with Success"),
    })
    @PostMapping("/transfer")
    public String transferer(@RequestParam String source,
                             @RequestParam String destination,
                             @RequestParam double montant) {
        bankService.transfererArgent(source, destination, montant);
        return "Transfert réussi";
    }

    @GetMapping("/accounts")
    public List<CompteBancaire> getAllAccounts() {
        return compteBancaireService.getAll();
    }

    @GetMapping("/accounts/{numero}")
    public CompteBancaire getAccount(@PathVariable String numero) {
        return compteBancaireService.getByNumeroCompte(numero);
    }
}