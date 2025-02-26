package ru.xast.SkillSwap.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.xast.SkillSwap.models.PersInf;
import ru.xast.SkillSwap.models.Users;
import ru.xast.SkillSwap.repositories.PersInfRepository;
import ru.xast.SkillSwap.repositories.UsersRepository;
import java.io.IOException;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UsersRepository usersRepository;
    private final PersInfRepository persInfRepository;

    @Autowired
    public CustomAuthSuccessHandler( UsersRepository usersRepository,PersInfRepository persInfRepository) {
        this.usersRepository = usersRepository;
        this.persInfRepository = persInfRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        Users user = usersRepository.findByUsername(username).orElse(null);
        if(user == null) {
            response.sendRedirect("auth/login");
        }
        PersInf persInf = persInfRepository.findPersInfByUserId(user.getId());
        if(persInf != null) {
            response.sendRedirect("/persInf");
        }else{
            response.sendRedirect("/persInf/new");
        }

    }
}
