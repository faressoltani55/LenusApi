package com.osc.lenus.LenusBackend.model.central;


import com.osc.lenus.LenusBackend.payload.utilities.ClientCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username = "";

    @Indexed(unique = true)
    private String email = "";

    private String password = "";

    private String firstName = "";
    private String lastName = "";
    private String imgUrl = "";
    private String birthDate = "";

    @Indexed(unique = true)
    private String passport = "";

    @Indexed(unique = true)
    private String idCard = "";

    @DBRef
    private List<Preference> preferences = new ArrayList<>();

    @DBRef
    private Address address = null;

    @DBRef
    private List<Response> quizAnswers = new ArrayList<>();

    @DBRef
    private List<Feedback> feedbacks = new ArrayList<>();

    @DBRef
    private List<Request> requests = new ArrayList<>();

    public Client(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Client(ClientCard clientCard) {
        this.email = clientCard.getEmail();
        this.firstName = clientCard.getFirstName();
        this.lastName = clientCard.getLastName();
        this.idCard = clientCard.getCin();
        this.passport = clientCard.getPassport();
        this.imgUrl = clientCard.getImgUrl();
        this.birthDate = clientCard.getBirthDate();
    }
}
