package de.akhmadjonov.project.FirstSecurityApp.controllers;

import de.akhmadjonov.project.FirstSecurityApp.dto.AuthenticationDTO;
import de.akhmadjonov.project.FirstSecurityApp.dto.PersonDTO;
import de.akhmadjonov.project.FirstSecurityApp.models.Person;
import de.akhmadjonov.project.FirstSecurityApp.security.JWTUtil;
import de.akhmadjonov.project.FirstSecurityApp.services.AdminService;
import de.akhmadjonov.project.FirstSecurityApp.services.PersonDetailsService;
import de.akhmadjonov.project.FirstSecurityApp.services.RegistrationService;
import de.akhmadjonov.project.FirstSecurityApp.util.PersonValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;
    private final AdminService adminService;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(PersonValidator personValidator, ModelMapper modelMapper, AuthenticationManager authenticationManager, JWTUtil jwtUtil, PersonDetailsService personDetailsService, AdminService adminService, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.personDetailsService = personDetailsService;
        this.adminService = adminService;
        this.registrationService = registrationService;
    }
//
//    @GetMapping("/login")
//    public String loginPage() {
//        return "auth/login";
//    }
//    @GetMapping("/registration")
//    public String register(@RequestBody PersonDTO personDTO) {
//        return "auth/registration";
//    }

    @GetMapping("/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", e.getMessage());
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("token", token);
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return Map.of("message", "The data invalid");
        }

        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getUsername());

        return Map.of("jwt-token", token);
    }

    @GetMapping("/admin")
    public String adminPage(Model model, HttpServletRequest servletRequest) {
        HttpSession sessions = servletRequest.getSession();

        adminService.doAdminStuff();
        model.addAttribute("personList", personDetailsService.findAll());
        return "admin/index";
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    private Authentication convertToAuthentication(AuthenticationDTO authenticationDTO) {
        return modelMapper.map(authenticationDTO, Authentication.class);
    }
}
