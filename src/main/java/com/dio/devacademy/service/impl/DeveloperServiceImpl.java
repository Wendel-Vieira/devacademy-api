package com.dio.devacademy.service.impl;

import com.dio.devacademy.controller.exception.BusinessException;
import com.dio.devacademy.controller.exception.ResourceNotFoundException;
import com.dio.devacademy.domain.model.Developer;
import com.dio.devacademy.domain.repository.DeveloperRepository;
import com.dio.devacademy.service.DeveloperService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepository;

    public DeveloperServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Developer> findAllPaged(Pageable pageable) {
        return developerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Developer findById(Long id) {
        return developerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    @Transactional
    public Developer create(Developer developerToCreate) {
        if (developerToCreate.getId() != null && developerRepository.existsById(developerToCreate.getId())) {
            throw new BusinessException("Já existe um desenvolvedor cadastrado com o ID " + developerToCreate.getId());
        }

        if (developerRepository.existsByEmail(developerToCreate.getEmail())) {
            throw new BusinessException("Já existe um desenvolvedor cadastrado com o e-mail: " + developerToCreate.getEmail());
        }

        if (developerToCreate.getGithubUsername() != null && !developerToCreate.getGithubUsername().isBlank()) {
            if (developerRepository.existsByGithubUsername(developerToCreate.getGithubUsername())) {
                throw new BusinessException("Já existe um desenvolvedor cadastrado com o usuário GitHub: " + developerToCreate.getGithubUsername());
            }
        }

        return developerRepository.save(developerToCreate);
    }

    @Override
    @Transactional
    public Developer update(Long id, Developer developerToUpdate) {
        Developer existing = findById(id);

        if (!existing.getEmail().equalsIgnoreCase(developerToUpdate.getEmail())) {
            if (developerRepository.existsByEmail(developerToUpdate.getEmail())) {
                throw new BusinessException("Já existe um desenvolvedor cadastrado com o e-mail: " + developerToUpdate.getEmail());
            }
        }

        if (developerToUpdate.getGithubUsername() != null && !developerToUpdate.getGithubUsername().equalsIgnoreCase(existing.getGithubUsername())) {
            if (developerRepository.existsByGithubUsername(developerToUpdate.getGithubUsername())) {
                throw new BusinessException("Já existe um desenvolvedor cadastrado com o usuário GitHub: " + developerToUpdate.getGithubUsername());
            }
        }

        existing.setName(developerToUpdate.getName());
        existing.setEmail(developerToUpdate.getEmail());
        existing.setGithubUsername(developerToUpdate.getGithubUsername());

        if (developerToUpdate.getPlan() != null) {
            existing.setPlan(developerToUpdate.getPlan());
        }

        if (developerToUpdate.getWallet() != null) {
            existing.setWallet(developerToUpdate.getWallet());
        }

        if (developerToUpdate.getSkills() != null) {
            existing.getSkills().clear();
            existing.getSkills().addAll(developerToUpdate.getSkills());
        }

        if (developerToUpdate.getCertifications() != null) {
            existing.getCertifications().clear();
            existing.getCertifications().addAll(developerToUpdate.getCertifications());
        }

        if (developerToUpdate.getAnnouncements() != null) {
            existing.getAnnouncements().clear();
            existing.getAnnouncements().addAll(developerToUpdate.getAnnouncements());
        }

        return developerRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Developer developer = findById(id);
        developerRepository.delete(developer);
    }
}
