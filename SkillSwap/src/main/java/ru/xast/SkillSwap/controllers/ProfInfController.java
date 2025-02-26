package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
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
        model.addAttribute("profInf", profInfService.findAll());
        return "profInf/index";
    }

    /*@GetMapping()
    public String index(Model model,
                        @RequestParam(value = "sort_by_rating", required = false) boolean sortByRating){
        model.addAttribute("profInf", profInfService.findAll(sortByRating));
        return "profInf/index";
    }*/

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("profInf", profInfService.findOne(id));
        return "profInf/show";
    }

    @GetMapping("/new")
    public String newSkill(@ModelAttribute("profInf") ProfInf profInf) {
        return "profInf/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("profInf") @Valid ProfInf profInf, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "profInf/new";
        }

        Users user = userDetailsService.getCurrentUser();
        profInf.setUser(user);


        PersInf persInf = persInfService.findPersInfByUserId(user.getId());

        profInf.setPers(persInf);

        profInfService.save(profInf);
        return "redirect:/profInf";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {

        Users currentUser = userDetailsService.getCurrentUser();

        ProfInf existingProfInf = profInfService.findOne(id);

        if(!existingProfInf.getUser().getId().equals(currentUser.getId())){
            return "redirect:/error/mismatchid";
        }

        model.addAttribute("profInf", profInfService.findOne(id));
        return "profInf/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("profInf") @Valid ProfInf profInf, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "profInf/edit";
        }

        Users user = userDetailsService.getCurrentUser();

        profInf.setUser(user);

        PersInf persInf = persInfService.findPersInfByUserId(user.getId());

        profInf.setPers(persInf);

        profInfService.update(id, profInf);
        return "redirect:/profInf";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {

        Users currentUser = userDetailsService.getCurrentUser();

        ProfInf existingProfInf = profInfService.findOne(id);

        if (!existingProfInf.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/error/mismatchid";
        }

        profInfService.delete(id);
        return "redirect:/profInf";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "profInf/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("skillName") String skillName){
        model.addAttribute("profInf", profInfService.searchBySkillName(skillName));
        return "profInf/search";
    }

}
