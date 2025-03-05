package ru.xast.SkillSwap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.SkillExchange;
import ru.xast.SkillSwap.repositories.SkillExchangeRepository;

@Service
@Transactional
public class SkillExchangeService {

    private final SkillExchangeRepository skillExchangeRepository;

    @Autowired
    public SkillExchangeService(SkillExchangeRepository skillExchangeRepository) {
        this.skillExchangeRepository = skillExchangeRepository;
    }

    public void save(SkillExchange skillExchange) {
        skillExchangeRepository.save(skillExchange);
    }

}
