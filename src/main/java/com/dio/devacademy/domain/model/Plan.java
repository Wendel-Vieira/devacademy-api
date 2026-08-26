package com.dio.devacademy.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do plano é obrigatório (ex: Starter, Pro, Enterprise)")
    private String name;

    @NotNull(message = "O preço mensal é obrigatório")
    @PositiveOrZero(message = "O preço deve ser zero ou positivo")
    private BigDecimal price;

    @PositiveOrZero(message = "Os créditos de mentoria devem ser zero ou positivos")
    private Integer mentorshipCredits = 0;

    private Boolean active = true;
}
