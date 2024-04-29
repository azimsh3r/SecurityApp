package de.akhmadjonov.project.FirstSecurityApp.security;

import de.akhmadjonov.project.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider{

    private final PersonDetailsService personDetailsService;

    @Autowired
    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // This method authenticate will be call automatically be Spring Security
    // logic of authentication
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails details = personDetailsService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();

        if(!password.equals(details.getPassword())) {
            throw new BadCredentialsException("The login credentials are incorrect");
        }

        return new UsernamePasswordAuthenticationToken(details, password,
                Collections.emptyList());
    }

    // for what cases this authProvider is suitable
    // in our case every case is handled here
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
