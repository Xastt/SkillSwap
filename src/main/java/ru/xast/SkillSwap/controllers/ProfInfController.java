package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.SkillSwap.models.ProfInf;
import ru.xast.SkillSwap.services.ProfInfService;

import java.util.UUID;

@Controller
@RequestMapping("/profInf")
public class ProfInfController {

    private final ProfInfService profInfService;

    @Autowired
    public ProfInfController(ProfInfService profInfService) {
        this.profInfService = profInfService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("profInf", profInfService.findAll());
        return "profInf/index";
    }

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

        profInfService.save(profInf);
        return "redirect:/profInf";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("profInf", profInfService.findOne(id));
        return "profInf/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("profInf") @Valid ProfInf profInf, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "profInf/edit";
        }

        profInfService.update(id, profInf);
        return "redirect:/profInf";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        profInfService.delete(id);
        return "redirect:/profInf";
    }
}
