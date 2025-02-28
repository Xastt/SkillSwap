package ru.xast.SkillSwapMailSender.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.xast.SkillSwapMailSender.models.Mail;

@Slf4j
@Service
public class KafkaConsumerService {

    private static final String MSG_TOPIC = "greetingsMsg";
    private static final String MSG_GROUP_ID = "msg-group";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;

    public KafkaConsumerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = MSG_TOPIC, groupId = MSG_GROUP_ID)
    public void listen(String message){
        try{
            Mail mail = objectMapper.readValue(message, Mail.class);
            emailService.sendHtmlEmail(mail);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error deserializing message: {}", e.getMessage());
        }
    }
}
