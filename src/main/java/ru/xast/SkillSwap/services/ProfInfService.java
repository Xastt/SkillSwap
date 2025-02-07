package ru.xast.SkillSwap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xast.SkillSwap.repositories.ProfInfRepository;

@Service
public class ProfInfService {

    private final ProfInfRepository profInfRepository;

    @Autowired
    public ProfInfService(ProfInfRepository profInfRepository) {
        this.profInfRepository = profInfRepository;
    }
}
