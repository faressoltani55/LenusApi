package com.osc.lenus.LenusBackend.model.central;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Responses")
@Data
public class Response {
    @Id
    private String id;

    private String question;
    private String[] possibleAnswers = new String[4];
    private String[] imgUrls = new String[4];
    private int[] answers = new int[4];

}
