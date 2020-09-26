package com.osc.lenus.LenusBackend.model.central;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.List;

@Document(collection = "HotelsChain")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelsChain {
    @Id
    private String id;
    @Email
    private String email;
    @Indexed(unique = true)
    private String name;

    private String description;

    @DBRef
    private List<Hotel> hotels;
}
