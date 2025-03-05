package ru.xast.SkillSwap.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "skillexchange")
public class SkillExchange {

    @Id
    @Column(name = "exchangeid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID exchangeId;

    @Column(name = "skilloffered")
    private String skillOffered;

    @Column(name = "useroffering")
    private UUID userOffering;

    @Column(name = "userrequesting")
    private UUID userRequesting;

    public SkillExchange() {}

    public SkillExchange(String skillOffered, UUID userOffering, UUID userRequesting) {
        this.skillOffered = skillOffered;
        this.userOffering = userOffering;
        this.userRequesting = userRequesting;
    }

}
