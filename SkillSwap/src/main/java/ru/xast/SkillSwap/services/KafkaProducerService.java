package ru.xast.SkillSwap.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.xast.SkillSwap.models.Mail;

@Slf4j
@Service
public class KafkaProducerService {

    private static final String MSG_TOPIC = "greetingsMsg";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendHelloEmail(String email, String name) {
        try {
            Mail mail = new Mail();
            mail.setTo(new String[]{email});
            mail.setSubject("Greetings");
            mail.setBody("<h1>Hello, " + name + " !</h1>"+
                    "<p>We are glad to see you on the SkillSwap platform.<p>"
                    + "<p>Have a nice time!<p>");
            String message = objectMapper.writeValueAsString(mail);
            kafkaTemplate.send(MSG_TOPIC, message);
            log.info("Hello message sent to Kafka: {}", message);
        } catch (JsonProcessingException e) {
            log.error("Error serializing hello message: {}", e.getMessage());
        }
    }

    public void sendConnectEmail(String email, String name) {
        try {
            Mail mail = new Mail();
            mail.setTo(new String[]{email});
            mail.setSubject("Greetings");
            mail.setBody("<h1>Hello, " + name + " !</h1>"+
                    "<p>Your service is interested<p>"
                    + "<p>Expect a call or email from the user.<p>");

            String message = objectMapper.writeValueAsString(mail);
            kafkaTemplate.send(MSG_TOPIC, message);
            log.info("Connect message sent to Kafka: {}", message);
        } catch (JsonProcessingException e) {
            log.error("Error serializing connect message: {}", e.getMessage());
        }
    }

}
