package com.dio.devacademy.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends BaseItem {

    private String category;

    @Min(value = 1, message = "O nível mínimo é 1")
    @Max(value = 5, message = "O nível máximo é 5")
    private Integer level = 1;
}
