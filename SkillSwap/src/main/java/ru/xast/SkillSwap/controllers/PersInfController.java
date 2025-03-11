package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.models.Users;
import ru.xast.SkillSwap.services.KafkaProducerService;
import ru.xast.SkillSwap.services.PersInfService;
import ru.xast.SkillSwap.services.ProfInfService;
import ru.xast.SkillSwap.services.UsersDetailsService;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/persInf")
public class PersInfController {

    private final KafkaProducerService kafkaProducerService;
    private final PersInfService persInfService;
    private final UsersDetailsService userDetailsService;

    @Autowired
    public PersInfController(KafkaProducerService kafkaProducerService, PersInfService persInfService, UsersDetailsService userDetailsService) {
        this.kafkaProducerService = kafkaProducerService;
        this.persInfService = persInfService;

        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String index(Model model){
        try {
            model.addAttribute("persInf", persInfService.findAll());
            return "persInf/index";
        } catch (Exception e) {
            log.error("Error loading persInf index page", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {

        try {
            Users currentUser = userDetailsService.getCurrentUser();
            PersInf existingPersInf = persInfService.findOne(id);

            if (!existingPersInf.getUser().getId().equals(currentUser.getId())) {
                return "redirect:/error/mismatchid";
            }

            model.addAttribute("persInf", existingPersInf);
            model.addAttribute("profInf", persInfService.getSkillsByPersonId(id));
            return "persInf/show";
        } catch (Exception e) {
            log.error("Error loading persInf with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("persInf") PersInf persInf) {
        return "persInf/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("persInf") @Valid PersInf persInf, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "persInf/new";
            }

            Users user = userDetailsService.getCurrentUser();
            persInf.setUser(user);

            //kafkaProducerService.sendHelloEmail(persInf.getEmail(), persInf.getName());
            persInfService.save(persInf);
            return "redirect:/persInf";
        } catch (Exception e) {
            log.error("Error creating persInf", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        try {
            PersInf existingPersInf = persInfService.findOne(id);
            model.addAttribute("persInf", existingPersInf);
            return "persInf/edit";
        } catch (Exception e) {
            log.error("Error loading edit page for persInf with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("persInf") @Valid PersInf persInf, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "persInf/edit";
            }

            Users user = userDetailsService.getCurrentUser();
            persInf.setUser(user);

            persInfService.update(id, persInf);
            return "redirect:/persInf";
        } catch (Exception e) {
            log.error("Error updating persInf with id: {}", id, e);
            return "redirect:/error/retry";
        }

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        try {
            Users currentUser = userDetailsService.getCurrentUser();
            PersInf existingPersInf = persInfService.findOne(id);

            if (!existingPersInf.getUser().getId().equals(currentUser.getId())) {
                return "redirect:/error/mismatchid";
            }

            persInfService.delete(id);
            return "redirect:/persInf";
        } catch (Exception e) {
            log.error("Error deleting persInf with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/search")
    public String searchPage(){
        return "persInf/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("surname") String surname){
        try {
            model.addAttribute("persInf", persInfService.searchBySurname(surname));
            return "persInf/search";
        } catch (Exception e) {
            log.error("Error searching persInf by surname: {}", surname, e);
            return "redirect:/error/retry";
        }
    }
}
