package ru.xast.SkillSwap.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.SkillExchange;
import ru.xast.SkillSwap.repositories.SkillExchangeRepository;

@Slf4j
@Service
@Transactional
public class SkillExchangeService {

    private final SkillExchangeRepository skillExchangeRepository;

    @Autowired
    public SkillExchangeService(SkillExchangeRepository skillExchangeRepository) {
        this.skillExchangeRepository = skillExchangeRepository;
    }

    public void save(SkillExchange skillExchange) {
        try {
            skillExchangeRepository.save(skillExchange);
            log.info("Skill exchange saved");
        }catch (Exception e) {
            log.error("Error saving SkillExchange: {}", e.getMessage());
            throw new RuntimeException("Error saving SkillExchange: " + e.getMessage());
        }
    }

}
