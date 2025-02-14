package ru.xast.SkillSwap.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.xast.SkillSwap.models.Users;
import ru.xast.SkillSwap.services.UsersDetailsService;

@Component
public class UsersValidator implements Validator {

    private final UsersDetailsService usersDetailsService;

    @Autowired
    public UsersValidator(final UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users users = (Users) o;
        try{
            usersDetailsService.loadUserByUsername(users.getUsername());
        }catch(UsernameNotFoundException e){
            return;
        }
        errors.rejectValue("username", "", "A person by that name already exists!");
    }
}
