package ru.xast.SkillSwap.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.xast.SkillSwap.models.Users;
import ru.xast.SkillSwap.services.ProfInfService;
import ru.xast.SkillSwap.services.RegistrationService;
import ru.xast.SkillSwap.util.UsersValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final UsersValidator usersValidator;
    private final ProfInfService profInfService;

    @Autowired
    public AuthController(RegistrationService registrationService, UsersValidator usersValidator, ProfInfService profInfService) {
        this.registrationService = registrationService;
        this.usersValidator = usersValidator;
        this.profInfService = profInfService;
    }

    @GetMapping("/welcome")
    public String welcomePage(Model model){
        model.addAttribute("profInf", profInfService.findAll());
        return "auth/welcome";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("users") Users users) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("users") @Valid Users users, BindingResult bindingResult) {

        usersValidator.validate(users, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        registrationService.register(users);

        return "redirect:/auth/login";
    }
}
