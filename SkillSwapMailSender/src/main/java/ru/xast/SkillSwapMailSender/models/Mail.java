package ru.xast.SkillSwapMailSender.models;

import lombok.*;

@Getter
@Setter
public class Mail {
    private String[] to;
    private String subject;
    private String body;
}
