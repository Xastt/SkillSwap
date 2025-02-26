package ru.xast.SkillSwap.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "profinf")
public class ProfInf {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pers_id", nullable = false)
    private PersInf pers;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "skillname")
    @NotEmpty(message = "Enter your SkillName!")
    @Size(min = 2, max = 60, message = "Your SkillName should be between 2 and 60!")
    private String skillName;

    @Column(name = "skilldescription")
    @NotEmpty(message = "Enter your SkillDescription!")
    @Size(max = 1000, message = "Your SkillDescription should be between 0 and 1000!")
    private String skillDescription;

    @Column(name = "cost")
    @Min(value = 0, message = "Your cost should be more than 0!")
    private Double cost;

    @Column(name = "persdescription")
    @NotEmpty(message = "Enter your PersDescription!")
    @Size(max = 1000, message = "Your PersDescription should be between 0 and 1000!")
    private String persDescription;

    @Column(name = "exp")
    @Min(value = 0, message = "Your experience should be more than 0!")
    private Double exp;

    @Column(name = "rating")
    private Double rating;

    @PrePersist
    public void prePersist() {
        if (this.rating == null) {
            this.rating = 0.0;
        }
    }

    public ProfInf() {}

    public ProfInf(PersInf pers, Users user, String skillName, Double cost, String skillDescription, String persDescription, Double exp) {
        this.pers = pers;
        this.user = user;
        this.skillName = skillName;
        this.cost = cost;
        this.skillDescription = skillDescription;
        this.persDescription = persDescription;
        this.exp = exp;
        this.rating = 0.0;
    }
}
