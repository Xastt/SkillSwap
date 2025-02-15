package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.models.Users;
import ru.xast.SkillSwap.services.PersInfService;
import ru.xast.SkillSwap.services.ProfInfService;
import ru.xast.SkillSwap.services.UsersDetailsService;
import java.util.UUID;

@Controller
@RequestMapping("/persInf")
public class PersInfController {

    private final PersInfService persInfService;
    private final ProfInfService profInfService;
    private final UsersDetailsService userDetailsService;

    @Autowired
    public PersInfController(PersInfService persInfService, ProfInfService profInfService, UsersDetailsService userDetailsService) {
        this.persInfService = persInfService;
        this.profInfService = profInfService;

        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("profInf", profInfService.findAll());
        return "persInf/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("persInf", persInfService.findOne(id));
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

        Users user = userDetailsService.getCurrentUser();

        persInf.setUser(user);

        persInfService.save(persInf);
        return "redirect:/persInf";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {

        Users currentUser = userDetailsService.getCurrentUser();

        PersInf existingPersInf = persInfService.findOne(id);

        // Проверяем совпадение user_id
        if (!existingPersInf.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/error/mismatchid"; // Перенаправление на страницу ошибки
        }

        model.addAttribute("persInf", existingPersInf);
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

        Users currentUser = userDetailsService.getCurrentUser();

        PersInf existingPersInf = persInfService.findOne(id);

        // Проверяем совпадение user_id
        if (!existingPersInf.getUser().getId().equals(currentUser.getId())) {
            // Обработка случая, когда идентификаторы не совпадают, можно выкинуть исключение
            return "redirect:/error/mismatchid";
        }


        persInfService.delete(id);
        return "redirect:/persInf";
    }


}
