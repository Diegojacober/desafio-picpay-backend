package com.diegojacober.desafiopicpay.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.diegojacober.desafiopicpay.domain.transaction.Transaction;
import com.diegojacober.desafiopicpay.domain.user.User;
import com.diegojacober.desafiopicpay.dtos.transaction.TransactionDTO;
import com.diegojacober.desafiopicpay.repositories.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionRepository repository;

    @Value("${services.authorizeTransactions.url}")
    private String authorizeTransactionsURL;

    public Transaction create(TransactionDTO transaction) throws Exception {
        User sender = userService.findUserById(transaction.senderId());
        User receiver = userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = authorizeTransaction(sender, transaction.value());
        if (!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        repository.save(newTransaction);
        userService.saveUser(receiver);
        userService.saveUser(sender);
        
        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationReponse = restTemplate.getForEntity(authorizeTransactionsURL, Map.class);

        if (authorizationReponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationReponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }
        return false;
    }
}
