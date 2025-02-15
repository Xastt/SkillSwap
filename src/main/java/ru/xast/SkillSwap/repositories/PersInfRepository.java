package ru.xast.SkillSwap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xast.SkillSwap.models.PersInf;

import java.util.UUID;

public interface PersInfRepository extends JpaRepository<PersInf, UUID> {
    PersInf findPersInfByUserId(UUID userId);
}
