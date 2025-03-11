package ru.xast.SkillSwap.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.Review;
import ru.xast.SkillSwap.repositories.ReviewRepository;

@Slf4j
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void save(Review review) {
        try {
            reviewRepository.save(review);
            log.info("Review saved");
        }catch (Exception e) {
            log.error("Error saving Review: {}", e.getMessage());
            throw new RuntimeException("Failed to save Review");
        }
    }
}
