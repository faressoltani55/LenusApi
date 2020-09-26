package com.osc.lenus.LenusBackend.model.local;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Recommendations")
@Data
public class Recommandation {
    private String id;
    private String resCode;
    private String name;
    private String imgUrl;
    private String description;
}
