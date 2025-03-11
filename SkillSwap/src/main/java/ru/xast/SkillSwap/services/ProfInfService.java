package ru.xast.SkillSwap.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.ProfInf;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.repositories.PersInfRepository;
import ru.xast.SkillSwap.repositories.ProfInfRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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
        try{
            profInfRepository.save(profInf);
            log.info("ProfInf saved, id: {}", profInf.getId());
        }catch(Exception e){
            log.error("Error saving ProfInf: {}", e.getMessage());
            throw new RuntimeException("Failed to saved ProfInf: " + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public ProfInf findOne(UUID id){
        try {
            Optional<ProfInf> profInf = profInfRepository.findById(id);
            if(profInf.isPresent()){
                log.info("ProfInf found, id: {}", id);
                return profInf.get();
            }else{
                log.warn("ProfInf not found, id: {}", id);
                return null;
            }
        }catch(Exception e){
            log.error("Error finding ProfInf: {}", e.getMessage());
            throw new RuntimeException("Failed to find ProfInf: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ProfInf findProfInfByUserId(UUID userId) {
        try{
            ProfInf profInf = profInfRepository.findProfInfByUserId(userId);
            if(profInf != null){
                log.info("ProfInf found with id: {}", profInf.getId());
            }else{
                log.warn("ProfInf not found with id: {}", userId);
            }
            return profInf;
        }catch(Exception e){
            log.error("Error in finding ProfInf: {}", e.getMessage());
            throw new RuntimeException("Failed to find ProfInf: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ProfInf findProfInfByPersId(UUID userId) {
        try{
            ProfInf profInf = profInfRepository.findProfInfByPersId(userId);
            if(profInf != null){
                log.info("ProfInf found with id : {}", profInf.getId());
            }else {
                log.warn("ProfInf not found with id : {}", userId);
            }
            return profInf;
        }catch(Exception e){
            log.error("Error in finding ProfInf : {}", e.getMessage());
            throw new RuntimeException("Failed to find ProfInf: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<ProfInf> findAll() {
        try{
            List<ProfInf> profInfList = profInfRepository.findAll();
            log.info("ProfInf found {} records", profInfList.size());
            return profInfList;
        }catch(Exception e){
            log.error("Error finding all ProfInf records: {}", e.getMessage());
            throw new RuntimeException("Failed to find all ProfInf records: " + e.getMessage());
        }
    }

    public void update(UUID id, ProfInf updatedProfInf){
        try{
            updatedProfInf.setId(id);
            profInfRepository.save(updatedProfInf);
            log.info("ProfInf updated, id: {}", id);
        }catch(Exception e){
            log.error("Error updating ProfInf: {}", e.getMessage());
            throw new RuntimeException("Failed to update ProfInf: " + e.getMessage());
        }
    }

    public void delete(UUID id){
        try{
            profInfRepository.deleteById(id);
            log.info("ProfInf deleted, id: {}", id);
        }catch(Exception e){
            log.error("Error deleting ProfInf: {}", e.getMessage());
            throw new RuntimeException("Failed to delete ProfInf: " + e.getMessage());
        }
    }

    public List<ProfInf> searchBySkillName(String skillName) {
        try {
            List<ProfInf> profInfList = profInfRepository.findBySkillNameStartingWith(skillName);
            log.info("ProfInf found {} records with SkillName", profInfList.size());
            return profInfList;
        }catch (Exception e){
            log.error("Error searching ProfInf by SkillName: {}", e.getMessage());
            throw new RuntimeException("Failed to search ProfInf by SkillName: " + e.getMessage());
        }
    }
}
