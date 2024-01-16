package com.diegojacober.desafiopicpay.domain.user;

import java.math.BigDecimal;

import com.diegojacober.desafiopicpay.dtos.user.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO dto) {
        this.balance = dto.balance();
        this.document = dto.document();
        this.email = dto.email();
        this.firstName = dto.firstName();
        this.lastName = dto.lastName();
        this.password = dto.password();
        this.userType = dto.userType();
    }
}
