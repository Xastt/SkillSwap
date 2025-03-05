package ru.xast.SkillSwap.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.xast.SkillSwap.util.Status;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "transactionid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(name = "changeid",
                referencedColumnName = "exchangeid",
                nullable = false
    )
    private SkillExchange changeId;

    public Transaction() {}

    public Transaction(Status status, SkillExchange changeId) {
        this.date = new Date();
        this.status = status;
        this.changeId = changeId;
    }
}
