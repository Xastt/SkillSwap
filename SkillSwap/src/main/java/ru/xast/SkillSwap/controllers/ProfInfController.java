package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.models.ProfInf;
import ru.xast.SkillSwap.models.Users;
import ru.xast.SkillSwap.services.PersInfService;
import ru.xast.SkillSwap.services.ProfInfService;
import ru.xast.SkillSwap.services.UsersDetailsService;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/profInf")
public class ProfInfController {

    private final ProfInfService profInfService;
    private final UsersDetailsService userDetailsService;
    private final PersInfService persInfService;

    @Autowired
    public ProfInfController(ProfInfService profInfService, UsersDetailsService userDetailsService, PersInfService persInfService) {
        this.profInfService = profInfService;
        this.userDetailsService = userDetailsService;
        this.persInfService = persInfService;
    }

    @GetMapping()
    public String index(Model model){
        try{
            model.addAttribute("profInf", profInfService.findAll());
            return "profInf/index";
        }catch(Exception e){
            log.error("Error loading persInf index page", e);
            return "redirect:/error/retry";
        }

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        try {
            model.addAttribute("profInf", profInfService.findOne(id));
            return "profInf/show";
        }catch(Exception e){
            log.error("Error loading persInf show page", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/new")
    public String newSkill(@ModelAttribute("profInf") ProfInf profInf) {
        return "profInf/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("profInf") @Valid ProfInf profInf, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "profInf/new";
            }

            Users user = userDetailsService.getCurrentUser();
            profInf.setUser(user);


            PersInf persInf = persInfService.findPersInfByUserId(user.getId());

            profInf.setPers(persInf);

            profInfService.save(profInf);
            return "redirect:/profInf";
        }catch(Exception e){
            log.error("Error creating persInf", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        try {

            Users currentUser = userDetailsService.getCurrentUser();

            ProfInf existingProfInf = profInfService.findOne(id);

            if (!existingProfInf.getUser().getId().equals(currentUser.getId())) {
                return "redirect:/error/mismatchid";
            }

            model.addAttribute("profInf", profInfService.findOne(id));
            return "profInf/edit";
        }catch(Exception e){
            log.error("Error loading persInf edit page", e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("profInf") @Valid ProfInf profInf, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "profInf/edit";
            }

            Users user = userDetailsService.getCurrentUser();

            profInf.setUser(user);

            PersInf persInf = persInfService.findPersInfByUserId(user.getId());

            profInf.setPers(persInf);

            profInfService.update(id, profInf);
            return "redirect:/profInf";
        }catch (Exception e){
            log.error("Error updating persInf", e);
            return "redirect:/error/retry";
        }

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        try {

            Users currentUser = userDetailsService.getCurrentUser();

            ProfInf existingProfInf = profInfService.findOne(id);

            if (!existingProfInf.getUser().getId().equals(currentUser.getId())) {
                return "redirect:/error/mismatchid";
            }

            profInfService.delete(id);
            return "redirect:/profInf";
        }catch(Exception e){
            log.error("Error deleting persInf", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/search")
    public String searchPage(){
        return "profInf/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("skillName") String skillName){
        try {
            model.addAttribute("profInf", profInfService.searchBySkillName(skillName));
            return "profInf/search";
        }catch(Exception e){
            log.error("Error loading persInf search page", e);
            return "redirect:/error/retry";
        }
    }

}
