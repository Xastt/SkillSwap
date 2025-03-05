package ru.xast.SkillSwap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.xast.SkillSwap.models.*;
import ru.xast.SkillSwap.services.*;
import ru.xast.SkillSwap.util.Status;

import java.util.UUID;

@Controller
@RequestMapping("/skillExchange")
public class SkillExchangeController {

    private final PersInfService persInfService;
    private final UsersDetailsService usersDetailsService;
    private final ProfInfService profInfService;
    private final SkillExchangeService skillExchangeService;
    private final TransactionService transactionService;

    @Autowired
    public SkillExchangeController(PersInfService persInfService, UsersDetailsService usersDetailsService, ProfInfService profInfService, SkillExchangeService skillExchangeService, TransactionService transactionService) {
        this.persInfService = persInfService;
        this.usersDetailsService = usersDetailsService;
        this.profInfService = profInfService;
        this.skillExchangeService = skillExchangeService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        PersInf persInf = persInfService.findOne(id);

        model.addAttribute("persInf", persInf);

        Users user = usersDetailsService.getCurrentUser();

        ProfInf userOffering = profInfService.findProfInfByUserId(persInf.getUser().getId());

        PersInf userRequesting = persInfService.findPersInfByUserId(user.getId());

        SkillExchange skillExchange = new SkillExchange(
                userOffering.getSkillName(),
                userOffering.getPers().getId(),
                userRequesting.getId()
        );

        Transaction transaction = new Transaction(Status.IN_PROCESS, skillExchange);

        skillExchangeService.save(skillExchange);
        transactionService.save(transaction);

        return "skillExchange/showPersInf";
    }

    @PostMapping("/{id}/success")
    public String success(@PathVariable("id") UUID id){
        Transaction transaction = transactionService.findByPersInfId(id);
        transaction.setStatus(Status.SUCCESS);
        transactionService.save(transaction);

        return "skillExchange/review";
    }

    @GetMapping("/{id}/cancel")
    public String cancel(@PathVariable("id") UUID id){
        Transaction transaction = transactionService.findByPersInfId(id);
        transaction.setStatus(Status.CANCELED);
        transactionService.save(transaction);

        return "skillExchange/canceled";
    }

}
