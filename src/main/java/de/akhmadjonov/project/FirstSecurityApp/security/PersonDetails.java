package de.akhmadjonov.project.FirstSecurityApp.security;

import de.akhmadjonov.project.FirstSecurityApp.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }


    // We need it for authorization
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }
    // Method which should return password
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    // to check if the account is not expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // to check if the account is nonLocked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    //Credentials not expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    // Account is active
    @Override
    public boolean isEnabled() {
        return true;
    }


    // needed for accessing data of authenticated people
    public Person getPerson() {
        return person;
    }
}
