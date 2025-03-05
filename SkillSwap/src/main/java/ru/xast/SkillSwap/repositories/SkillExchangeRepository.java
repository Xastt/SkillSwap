package ru.xast.SkillSwap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xast.SkillSwap.models.SkillExchange;

import java.util.UUID;

@Repository
public interface SkillExchangeRepository extends JpaRepository<SkillExchange, UUID> {
}
