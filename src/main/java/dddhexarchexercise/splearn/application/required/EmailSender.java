package dddhexarchexercise.splearn.application.required;

import dddhexarchexercise.splearn.domain.Email;

/**
 * 이메일 발송
 */
public interface EmailSender {
    void send(Email email, String subject, String body);
}
