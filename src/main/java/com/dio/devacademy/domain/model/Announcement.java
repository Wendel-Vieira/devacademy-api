package com.dio.devacademy.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_announcement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Announcement extends BaseItem {

    private LocalDateTime publishedAt = LocalDateTime.now();
    private String actionUrl;
}
