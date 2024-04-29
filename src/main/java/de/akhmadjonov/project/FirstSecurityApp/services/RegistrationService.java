package de.akhmadjonov.project.FirstSecurityApp.services;

import de.akhmadjonov.project.FirstSecurityApp.models.Person;
import de.akhmadjonov.project.FirstSecurityApp.respositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Person person) {
        person.setRole("ROLE_USER");
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }
}
