package ru.xast.SkillSwap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xast.SkillSwap.models.ProfInf;

import java.util.UUID;

@Repository
public interface ProfInfRepository extends JpaRepository<ProfInf, UUID> {
}
