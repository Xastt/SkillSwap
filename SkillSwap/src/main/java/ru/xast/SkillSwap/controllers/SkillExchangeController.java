package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.SkillSwap.models.*;
import ru.xast.SkillSwap.services.*;
import ru.xast.SkillSwap.util.Status;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/skillExchange")
public class SkillExchangeController {

    private final PersInfService persInfService;
    private final ReviewService reviewService;
    private final UsersDetailsService usersDetailsService;
    private final ProfInfService profInfService;
    private final SkillExchangeService skillExchangeService;
    private final TransactionService transactionService;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public SkillExchangeController(PersInfService persInfService, ReviewService reviewService,
                                   UsersDetailsService usersDetailsService, ProfInfService profInfService,
                                   SkillExchangeService skillExchangeService, TransactionService transactionService,
                                   KafkaProducerService kafkaProducerService) {
        this.persInfService = persInfService;
        this.reviewService = reviewService;
        this.usersDetailsService = usersDetailsService;
        this.profInfService = profInfService;
        this.skillExchangeService = skillExchangeService;
        this.transactionService = transactionService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        try {
            PersInf persInf = persInfService.findOne(id);

            model.addAttribute("persInf", persInf);

            Users user = usersDetailsService.getCurrentUser();

            ProfInf userOffering = profInfService.findProfInfByUserId(persInf.getUser().getId());

            PersInf userRequesting = persInfService.findPersInfByUserId(user.getId());


            if (userOffering.getPers().getId() == userRequesting.getId()) {
                return "/error/SameIdException";
            }

            SkillExchange skillExchange = new SkillExchange(
                    userOffering.getSkillName(),
                    userOffering.getPers().getId(),
                    userRequesting.getId()
            );

            Transaction transaction = new Transaction(Status.IN_PROCESS, skillExchange);

            skillExchangeService.save(skillExchange);
            transactionService.save(transaction);

            //kafkaProducerService.sendConnectEmail(userOffering.getPers().getEmail(), userOffering.getPers().getName());

            return "skillExchange/showPersInf";
        }catch (Exception e) {
            log.error("Error in show in SkillExchangeController", e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/{id}/success")
    public String success(@PathVariable("id") UUID id, Model model){
        try {
            Transaction transaction = transactionService.findByPersInfId(id);
            transaction.setStatus(Status.SUCCESS);
            transactionService.save(transaction);

            model.addAttribute("review", new Review());
            model.addAttribute("userEvaluatedId", id);

            return "skillExchange/review";
        }catch (Exception e) {
            log.error("Error in success in ReviewController", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/review")
    public String review(@ModelAttribute("review") Review review, Model model) {
        return "skillExchange/review";
    }

    @PostMapping("/review")
    public String submitReview(@ModelAttribute("review") @Valid Review review, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                return "skillExchange/review";
            }

            Users user = usersDetailsService.getCurrentUser();
            review.setReviewer(user.getId());

            if (review.getUserEvaluated() == null) {
                UUID userEvaluatedId = (UUID) model.getAttribute("userEvaluatedId");
                review.setUserEvaluated(userEvaluatedId);
            }

            reviewService.save(review);

            ProfInf profInf = profInfService.findProfInfByPersId(review.getUserEvaluated());

            Double ratingBefore = profInf.getRating();

            Double finalRating;
            if (ratingBefore == null || ratingBefore == 0.0) {
                finalRating = review.getRating();
            } else {
                finalRating = (ratingBefore + review.getRating()) / 2.0;
            }

            profInf.setRating(finalRating);
            profInfService.save(profInf);

            return "skillExchange/successDeal";
        }catch (Exception e) {
            log.error("Error in submitReview", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}/cancel")
    public String cancel(@PathVariable("id") UUID id){
        try {
            Transaction transaction = transactionService.findByPersInfId(id);
            transaction.setStatus(Status.CANCELED);
            transactionService.save(transaction);

            return "skillExchange/canceled";
        }catch (Exception e) {
            log.error("Error in cancel", e);
            return "redirect:/error/retry";
        }
    }

}
