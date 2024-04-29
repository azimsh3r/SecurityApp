package de.akhmadjonov.project.FirstSecurityApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name="Person")
public class Person {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotEmpty(message = "This field cannot be empty")
    @Column(name="username")
    @Size(min=2, max=200, message = "This username is invalid")
    String username;

    @NotEmpty(message = "This field cannot be empty")
    //@Size(min = 8, max = 30)
    @Column(name="password")
    String password;

    @Column(name="authority")
    String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
