package ru.xast.SkillSwap.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.Transaction;
import ru.xast.SkillSwap.repositories.TransactionRepository;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transaction transaction){
        try {
            transactionRepository.save(transaction);
            log.info("Transaction saved");
        }catch (Exception e){
            log.error("Error saving transaction: {}", e.getMessage());
            throw new RuntimeException("Error saving transaction: " + e.getMessage());
        }
    }

    public Transaction findByPersInfId(UUID persInfId){
        try {
            Transaction transaction = transactionRepository.findByUserOfferingId(persInfId);
            if(transaction != null){
                log.info("Transaction found");
            }else{
                log.warn("Transaction NOT found");
            }
            return transaction;
        }catch (Exception e){
            log.error("Error finding transaction: {}", e.getMessage());
            throw new RuntimeException("Error finding transaction: " + e.getMessage());
        }
    }
}
