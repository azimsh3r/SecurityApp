package de.akhmadjonov.project.FirstSecurityApp.respositories;

import de.akhmadjonov.project.FirstSecurityApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findPersonByUsername(String username);
}
