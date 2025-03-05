package ru.xast.SkillSwap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.Transaction;
import ru.xast.SkillSwap.repositories.TransactionRepository;

import java.util.UUID;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public Transaction findByPersInfId(UUID persInfId){
        return transactionRepository.findByUserOfferingId(persInfId);
    }
}
