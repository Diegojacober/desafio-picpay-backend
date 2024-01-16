package com.diegojacober.desafiopicpay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diegojacober.desafiopicpay.domain.transaction.Transaction;
import com.diegojacober.desafiopicpay.dtos.transaction.TransactionDTO;
import com.diegojacober.desafiopicpay.services.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionDTO transaction) throws Exception {
        Transaction newTransaction = transactionService.create(transaction);

        return ResponseEntity.status(HttpStatus.OK).body(newTransaction);
    }
}
