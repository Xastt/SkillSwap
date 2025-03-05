package ru.xast.SkillSwap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.xast.SkillSwap.models.Transaction;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t JOIN t.changeId se WHERE se.userOffering = :userOfferingId")
    Transaction findByUserOfferingId(@Param("userOfferingId") UUID userOfferingId);
}
