package com.osc.lenus.LenusBackend.config.others;

import com.osc.lenus.LenusBackend.model.central.Client;
import com.osc.lenus.LenusBackend.model.local.Reservation;
import com.osc.lenus.LenusBackend.repositories.central.ClientRepository;
import com.osc.lenus.LenusBackend.repositories.local.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder encoder;


    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lenus.team@gmail.com");
        message.setTo("");
        message.setSubject("Dear");
        message.setText("Service Lenus");
        emailSender.send(message);
    }

    public void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = this.emailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo("faressoltani.officiel@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Lenus,</h1>Stay Safe and Enjoy the Experience.",
                true);
        helper.setText("<h1>Lenus,</h1>Stay Safe and Enjoy the Experience.",
                true);


        helper.addAttachment("logolenus.png", new ClassPathResource("logolenus.png"));

        emailSender.send(msg);

    }

    public void sendEmail(String reservationCode, String hotelName) throws MessagingException, IOException {
        Reservation reservation = this.reservationRepository.findByReservationCode(reservationCode);
        Client client = this.clientRepository.findById(reservation.getClientId()).get();
        MimeMessage msg = this.emailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(client.getEmail());

        helper.setSubject("Reservation in "+ hotelName);

        String header = "Hi "+client.getFirstName()+",<br><br>" +
                "Thank you for reserving in one of our partner hotels <strong>"+hotelName+"</strong> !<br>"+
                "We're very happy to make your stay as unique and secure as possible, and for that we highly recommend you to download our Lenus app from the App Store. You can find a link to it down below :<br>" +
                "Link<br><br>";
        String resDetails = "Your reservation will last from "+reservation.getArrivalDate() + " until "+ reservation.getExitDate() + ".<br>" +
                "Here are your reservation code : "+reservationCode+ ", and your group code : "+reservation.getGroupCode() + ", which you will use in the check-in process.<br><br>";
        String credentials = "We've automatically created for you a new account so you can already start your experience !<br>" +
                "Here are your login details :<br>" +
                "<ul><li>Login : "+client.getEmail()+"</li><li>Password : "+client.getPassword()+"</li></ul><br><br>";
        String end = "We're very excited to make your stay a one of a kind !<br><br>" +
                "<strong>Lenus</strong>,<br>Stay Safe, and Enjoy the Experience.";

        String text;
        if(client.getImgUrl().equals(""))
            text = header + credentials + resDetails + end;
        else text = header + resDetails + end;

        // true = text/html
        helper.setText(text,true);

        helper.addAttachment("logolenus.png", new ClassPathResource("logolenus.png"));

        emailSender.send(msg);
        System.out.println("Success");

        client.setPassword(encoder.encode(client.getPassword()));
        this.clientRepository.save(client);

    }
}
