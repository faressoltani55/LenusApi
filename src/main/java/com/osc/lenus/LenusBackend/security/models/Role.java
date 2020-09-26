package com.osc.lenus.LenusBackend.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Roles")
@Data
@AllArgsConstructor
public class Role {
    @Id
    private String id;

    private ERole name;
}
