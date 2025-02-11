package ru.xast.SkillSwap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.repositories.PersInfRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PersInfService {

    private final PersInfRepository persInfRepository;

    @Autowired
    public PersInfService(PersInfRepository persInfRepository) {
        this.persInfRepository = persInfRepository;
    }

    public void save(PersInf persInf) {
        persInfRepository.save(persInf);
    }

    @Transactional(readOnly = true)
    public PersInf findOne(UUID id) {
        return persInfRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PersInf> findAll() {
        return persInfRepository.findAll();
    }

    public void update(UUID id, PersInf updatedPersInf){
        updatedPersInf.setId(id);
        persInfRepository.save(updatedPersInf);
    }

    public void delete(UUID id) {
        persInfRepository.deleteById(id);
    }
}
