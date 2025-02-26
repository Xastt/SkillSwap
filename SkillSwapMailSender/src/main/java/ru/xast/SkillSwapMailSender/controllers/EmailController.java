package ru.xast.SkillSwapMailSender.controllers;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.xast.SkillSwapMailSender.models.Mail;
import ru.xast.SkillSwapMailSender.services.EmailService;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/html")
    public void sendHTMLEmail(@RequestBody Mail mail){
        try {
            emailService.sendHtmlEmail(mail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
