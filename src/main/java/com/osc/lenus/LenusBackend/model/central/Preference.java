package com.osc.lenus.LenusBackend.model.central;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Preference")
@Data
public class Preference {
    @Id
    private String id;

    private String key;

    private List<String> values;
}
