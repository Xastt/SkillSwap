package ru.xast.SkillSwap.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.models.ProfInf;
import ru.xast.SkillSwap.repositories.PersInfRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class PersInfService {

    private final PersInfRepository persInfRepository;

    @Autowired
    public PersInfService(PersInfRepository persInfRepository) {
        this.persInfRepository = persInfRepository;
    }

    public void save(PersInf persInf) {
        try{
            persInfRepository.save(persInf);
            log.info("PersInf saved, id: {}", persInf.getId());
        }catch(Exception e){
            log.error("Error saving PersInf: {}", e.getMessage());
            throw new RuntimeException("Failed to saved PersInf: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public PersInf findOne(UUID id) {
        try{
            Optional<PersInf> persInf = persInfRepository.findById(id);
            if(persInf.isPresent()){
                log.info("PersInf found, id: {}", id);
                return persInf.get();
            }else{
                log.warn("PersInf not found, id: {}", id);
                return null;
            }
        }catch(Exception e){
            log.error("Error finding PersInf with id: {}, {}", id, e.getMessage());
            throw new RuntimeException("Failed to find PersInf with id: " + id);
        }
    }

    @Transactional(readOnly = true)
    public PersInf findPersInfByUserId(UUID userId) {
        try{
            PersInf persInf = persInfRepository.findPersInfByUserId(userId);
            if(persInf != null){
                log.info("PersInf found with id: {}", userId);
            }else{
                log.warn("PersInf not found for user with id: {}", userId);
            }
            return persInf;
        }catch (Exception e){
            log.error("Error finding PersInf for user with id: {}, {}", userId, e.getMessage());
            throw new RuntimeException("Failed to find PersInf by user id", e);
        }
    }

    @Transactional(readOnly = true)
    public List<PersInf> findAll() {
        try {
            List<PersInf> persInfList = persInfRepository.findAll();
            log.info("Found {} PersInf records", persInfList.size());
            return persInfList;
        } catch (Exception e) {
            log.error("Error finding all PersInf records", e);
            throw new RuntimeException("Failed to find all PersInf records", e);
        }
    }

    public void update(UUID id, PersInf updatedPersInf){
        try {
            updatedPersInf.setId(id);
            persInfRepository.save(updatedPersInf);
            log.info("PersInf updated successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error updating PersInf with id: {}", id, e);
            throw new RuntimeException("Failed to update PersInf", e);
        }
    }

    public void delete(UUID id) {
        try {
            persInfRepository.deleteById(id);
            log.info("PersInf deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting PersInf with id: {}", id, e);
            throw new RuntimeException("Failed to delete PersInf", e);
        }
    }

    public List<PersInf> searchBySurname(String surname) {
        try {
            List<PersInf> persInfList = persInfRepository.findBySurnameStartingWith(surname);
            log.info("Found {} PersInf records with surname starting with: {}", persInfList.size(), surname);
            return persInfList;
        } catch (Exception e) {
            log.error("Error searching PersInf by surname: {}", surname, e);
            throw new RuntimeException("Failed to search PersInf by surname", e);
        }
    }

    public List<ProfInf> getSkillsByPersonId(UUID personId) {
        try {
            Optional<PersInf> persInf = persInfRepository.findById(personId);
            if (persInf.isPresent()) {
                Hibernate.initialize(persInf.get().getProvidedSkills());
                log.info("Found skills for PersInf with id: {}", personId);
                return persInf.get().getProvidedSkills();
            } else {
                log.warn("PersInf not found with id: {}", personId);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("Error finding skills for PersInf with id: {}", personId, e);
            throw new RuntimeException("Failed to find skills by PersInf id", e);
        }
    }
}
