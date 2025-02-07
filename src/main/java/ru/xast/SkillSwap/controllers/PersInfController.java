package ru.xast.SkillSwap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.xast.SkillSwap.services.PersInfService;

@Controller
@RequestMapping("/persInf")
public class PersInfController {

    private final PersInfService persInfService;

    @Autowired
    public PersInfController(PersInfService persInfService) {
        this.persInfService = persInfService;
    }
}
