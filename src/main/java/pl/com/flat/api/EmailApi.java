package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailApi {

    @Autowired
    private  JavaMailSender emailSender;

    public  void send(String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo("mszweda93@gmail.com"); 
        message.setSubject("Mieszkanie - aktualizacja."); 
        message.setText(text);
        emailSender.send(message);
    }
}