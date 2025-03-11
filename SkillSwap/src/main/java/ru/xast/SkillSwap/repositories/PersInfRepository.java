package ru.xast.SkillSwap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xast.SkillSwap.models.PersInf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersInfRepository extends JpaRepository<PersInf, UUID> {

    Optional<PersInf> findBySurname(String surName);

    PersInf findPersInfByUserId(UUID userId);

    List<PersInf> findBySurnameStartingWith(String surname);
}
