package ru.xast.SkillSwap.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.ProfInf;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.repositories.PersInfRepository;
import ru.xast.SkillSwap.repositories.ProfInfRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProfInfService {

    private final ProfInfRepository profInfRepository;
    private final PersInfRepository persInfRepository;

    @Autowired
    public ProfInfService(ProfInfRepository profInfRepository, PersInfRepository persInfRepository) {
        this.profInfRepository = profInfRepository;
        this.persInfRepository = persInfRepository;

    }

    public void save(ProfInf profInf){
        profInfRepository.save(profInf);
    }

    @Transactional(readOnly = true)
    public ProfInf findOne(UUID id){
        return profInfRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public ProfInf findProfInfByUserId(UUID userId) {
        return profInfRepository.findProfInfByUserId(userId);
    }

    @Transactional(readOnly = true)
    public ProfInf findProfInfByPersId(UUID userId) {
        return profInfRepository.findProfInfByPersId(userId);
    }

    @Transactional(readOnly = true)
    public List<ProfInf> findAll() {
        return profInfRepository.findAll();
    }

    public void update(UUID id, ProfInf updatedProfInf){
        updatedProfInf.setId(id);
        profInfRepository.save(updatedProfInf);
    }

    public void delete(UUID id){
        profInfRepository.deleteById(id);
    }

    public void saveWithPersInf(UUID persInfId, ProfInf profInf) {
        PersInf persInf = persInfRepository.findById(persInfId).orElse(null);
        if (persInf != null) {
            profInf.setPers(persInf);
            profInfRepository.save(profInf);
        } else {
            throw new EntityNotFoundException("Personal information not found for ID: " + persInfId);
        }
    }

    public List<ProfInf> searchBySkillName(String skillName) {
        return profInfRepository.findBySkillNameStartingWith(skillName);
    }

    public List<PersInf> findAll(boolean sortByRating){
        if(sortByRating){
            return persInfRepository.findAll(Sort.by("rating"));
        }else{
            return persInfRepository.findAll();
        }
    }
}
