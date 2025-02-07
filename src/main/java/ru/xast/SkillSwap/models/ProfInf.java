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
    @JoinColumn(name = "pers_id", referencedColumnName = "id")
    private PersInf pers_id;

    @Column(name = "skillname")
    @NotEmpty(message = "Enter your SkillName!")
    @Size(min = 2, max = 60, message = "Your SkillName should be between 2 and 60!")
    private String SkillName;

    @Column(name = "skilldescription")
    @NotEmpty(message = "Enter your SkillDescription!")
    @Size(max = 1000, message = "Your SkillDescription should be between 0 and 1000!")
    private String SkillDescription;

    @Column(name = "cost")
    @NotEmpty(message = "Enter the cost!")
    @Min(value = 0, message = "Your cost should be more than 0!")
    private Double cost;

    @Column(name = "persdescription")
    @NotEmpty(message = "Enter your PersDescription!")
    @Size(max = 1000, message = "Your PersDescription should be between 0 and 1000!")
    private String PersDescription;

    @Column(name = "exp")
    @NotEmpty(message = "Enter the experience!")
    @Min(value = 0, message = "Your experience should be more than 0!")
    private Double exp;

    @Column(name = "rating")
    private Double rating;

    public ProfInf() {}

    public ProfInf(PersInf pers_id, String skillName, Double cost, String skillDescription, String persDescription, Double exp) {
        this.pers_id = pers_id;
        SkillName = skillName;
        this.cost = cost;
        SkillDescription = skillDescription;
        PersDescription = persDescription;
        this.exp = exp;
        this.rating = 0.0;
    }
}
