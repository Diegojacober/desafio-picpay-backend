package com.diegojacober.desafiopicpay.dtos.user;

import java.math.BigDecimal;

import com.diegojacober.desafiopicpay.domain.user.UserType;

public record UserDTO(String firstName, String lastName, String document, String email, BigDecimal balance, String password, UserType userType) {
    
}
