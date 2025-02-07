package ru.xast.SkillSwap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xast.SkillSwap.repositories.PersInfRepository;

@Service
public class PersInfService {

    private final PersInfRepository persInfRepository;

    @Autowired
    public PersInfService(PersInfRepository persInfRepository) {
        this.persInfRepository = persInfRepository;
    }
}
