package pl.com.flat.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import pl.com.flat.model.Resident;

@Component
public class EmailService {

    private static final HashMap<String,String> messages = new HashMap<String,String>() {{
        put("settlements-add", "W systemie dodano nowy wydatek. Rozlicz go.");
    }};

    @Autowired private  JavaMailSender emailSender;

    public  void notify(Resident r, String name) {
        var msg  = messages.get(name);
        var text = msg == null ? name : msg;

        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(r.getEmail()); 
        message.setSubject("Mieszkanie - aktualizacja."); 
        message.setText(text);

        System.out.println(r.getEmail() + " - " + text);

        // emailSender.send(message);
    }
}