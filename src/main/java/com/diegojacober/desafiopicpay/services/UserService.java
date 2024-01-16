package com.diegojacober.desafiopicpay.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diegojacober.desafiopicpay.domain.user.User;
import com.diegojacober.desafiopicpay.domain.user.UserType;
import com.diegojacober.desafiopicpay.dtos.user.UserDTO;
import com.diegojacober.desafiopicpay.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo não logista não está autorizado a realizar transações");
        }

        if (sender.getBalance().compareTo(amount) <= 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception{
        return userRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado!"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User createUser(UserDTO user) {
        User newUser = new User(user);
        return userRepository.save(newUser);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
