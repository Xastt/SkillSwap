package ru.xast.SkillSwap.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "review")
public class Review {

    @Id
    @Column(name = "reviewid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewid;

    @Column(name = "rating")
    @Size(min = 1, max = 5, message = "Your rating should be between 1 and 5!")
    private Double rating;

    @Column(name = "comment")
    @NotEmpty(message = "Enter your comment!")
    private String comment;

    @Column(name = "reviewer")
    private UUID reviewer;

    @Column(name = "userevaluated")
    private UUID userEvaluated;

    public Review() {}

    public Review(Double rating, String comment, UUID reviewer, UUID userEvaluated) {
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.userEvaluated = userEvaluated;
    }

}
