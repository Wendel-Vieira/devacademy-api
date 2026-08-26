package com.dio.devacademy.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_certification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certification extends BaseItem {

    private String validationCode;
    private LocalDate issuedAt = LocalDate.now();
    private String issuer;
}
