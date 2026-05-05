package dddhexarchexercise.splearn.adapter.integration;

import dddhexarchexercise.splearn.application.required.EmailSender;
import dddhexarchexercise.splearn.domain.Email;
import org.springframework.stereotype.Component;

@Component
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email: " + email);
    }
}
