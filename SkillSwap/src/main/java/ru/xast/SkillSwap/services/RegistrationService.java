package ru.xast.SkillSwap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xast.SkillSwap.models.Users;
import ru.xast.SkillSwap.repositories.UsersRepository;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, UsersRepository usersRepository, KafkaProducerService kafkaProducerService) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Transactional
    public void register(Users users){
        String encodedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodedPassword);
        users.setRole("ROLE_USER");
        //kafkaProducerService.send(users.getUsername());
        usersRepository.save(users);
    }
}
