package com.dio.devacademy.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "O saldo de moedas deve ser positivo ou zero")
    private BigDecimal devCoins = BigDecimal.ZERO;

    @PositiveOrZero(message = "Os pontos de experiência (XP) devem ser positivos ou zero")
    private Integer xpPoints = 0;

    private String rankLevel = "JUNIOR";
}
