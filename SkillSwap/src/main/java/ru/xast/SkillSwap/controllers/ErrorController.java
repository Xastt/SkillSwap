package ru.xast.SkillSwap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/mismatchid")
    public String mismatchId() {
        return "error/mismatchid";
    }

    @GetMapping("/retry")
    public String retry() {
        return "error/retry";
    }
}
