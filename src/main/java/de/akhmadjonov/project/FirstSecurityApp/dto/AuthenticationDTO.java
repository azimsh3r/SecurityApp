package de.akhmadjonov.project.FirstSecurityApp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthenticationDTO {

    @NotNull(message = "Username cannot be empty")
    @Size(min=2 , max = 100, message = "The name cannot be of this length")
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
