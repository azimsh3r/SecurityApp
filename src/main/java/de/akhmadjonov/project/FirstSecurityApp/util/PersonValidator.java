package de.akhmadjonov.project.FirstSecurityApp.util;


import de.akhmadjonov.project.FirstSecurityApp.models.Person;
import de.akhmadjonov.project.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements  Validator{

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            personDetailsService.loadUserByUsername(((Person) target).getUsername());
        } catch (UsernameNotFoundException exception) {
            return;
        }

        errors.rejectValue("username", "", "chelovek s takim imenem est uje");
    }
}
