package com.osc.lenus.LenusBackend.model.central;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Document(collection = "SuperUsers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperUser {
    @Id
    private String id;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    private String password;
    private String firstName;
    private String lastName;

    public SuperUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
