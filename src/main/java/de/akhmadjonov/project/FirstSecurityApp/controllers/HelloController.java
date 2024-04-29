package de.akhmadjonov.project.FirstSecurityApp.controllers;

import de.akhmadjonov.project.FirstSecurityApp.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/getCred")
    public String getCredentials() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();


        System.out.println(personDetails.getUsername());
        System.out.println(personDetails.getPassword());

        return "hello";
    }
}
