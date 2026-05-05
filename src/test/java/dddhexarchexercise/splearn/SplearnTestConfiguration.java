package dddhexarchexercise.splearn;

import dddhexarchexercise.splearn.application.required.EmailSender;
import dddhexarchexercise.splearn.domain.MemberFixture;
import dddhexarchexercise.splearn.domain.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("Sending Test email: " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
