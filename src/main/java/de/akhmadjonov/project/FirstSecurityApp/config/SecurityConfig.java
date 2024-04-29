package de.akhmadjonov.project.FirstSecurityApp.config;

import de.akhmadjonov.project.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .logout( logOut -> {logOut.logoutUrl("/logout").logoutSuccessUrl("/hello");})
                .authorizeHttpRequests(rmr -> rmr.requestMatchers("/auth/login","/auth/registration" , "/error").permitAll()
                .anyRequest().hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"))
                .formLogin(formLogin -> formLogin
                 .loginPage("/auth/login")
                 .loginProcessingUrl("/process_login")
                 .defaultSuccessUrl("/hello", true)
                 .failureUrl("/auth/login?error"));

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(personDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}