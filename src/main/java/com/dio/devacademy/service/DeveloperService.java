package com.dio.devacademy.service;

import com.dio.devacademy.domain.model.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeveloperService {

    List<Developer> findAll();

    Page<Developer> findAllPaged(Pageable pageable);

    Developer findById(Long id);

    Developer create(Developer developerToCreate);

    Developer update(Long id, Developer developerToUpdate);

    void delete(Long id);
}
