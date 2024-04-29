package de.akhmadjonov.project.FirstSecurityApp.services;

import de.akhmadjonov.project.FirstSecurityApp.models.Person;
import de.akhmadjonov.project.FirstSecurityApp.respositories.PersonRepository;
import de.akhmadjonov.project.FirstSecurityApp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

// For service to be able to identify this service we should implement UserDetailsService
@Service
public class PersonDetailsService implements UserDetailsService {
    private PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findPersonByUsername(username);

        if(person.isEmpty()) {
            throw new UsernameNotFoundException("User is not found!");
        }
        return new PersonDetails(person.get());
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
