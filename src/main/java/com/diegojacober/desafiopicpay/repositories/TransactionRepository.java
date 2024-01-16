package com.diegojacober.desafiopicpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegojacober.desafiopicpay.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
}
