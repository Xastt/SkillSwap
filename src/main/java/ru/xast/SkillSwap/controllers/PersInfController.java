package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.models.ProfInf;
import ru.xast.SkillSwap.services.PersInfService;
import ru.xast.SkillSwap.services.ProfInfService;

import java.util.UUID;

@Controller
@RequestMapping("/persInf")
public class PersInfController {

    private final PersInfService persInfService;
    private final ProfInfService profInfService;

    @Autowired
    public PersInfController(PersInfService persInfService, ProfInfService profInfService) {
        this.persInfService = persInfService;
        this.profInfService = profInfService;
    }

    @GetMapping()
    public String index(){
        return "persInf/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("persInf", persInfService.findOne(id));
       // model.addAttribute("books", profInfService.getBooksByPersonId(id));
        return "persInf/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("persInf") PersInf persInf) {
        return "persInf/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("persInf") @Valid PersInf persInf, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "persInf/new";
        }

        persInfService.save(persInf);
        return "redirect:/persInf";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("persInf", persInfService.findOne(id));
        return "persInf/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("persInf") @Valid PersInf persInf, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "persInf/edit";
        }

        persInfService.update(id, persInf);
        return "redirect:/persInf";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        persInfService.delete(id);
        return "redirect:/persInf";
    }
}
