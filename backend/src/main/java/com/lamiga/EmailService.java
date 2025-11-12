package com.lamiga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String toEmail;
    private final String fromEmail;
    private final boolean enabled;

    @Autowired(required = false)
    public EmailService(
            JavaMailSender mailSender,
            @Value("${contact.email.to:}") String toEmail,
            @Value("${contact.email.from:}") String fromEmail,
            @Value("${contact.email.enabled:false}") boolean enabled
    ) {
        this.mailSender = mailSender;
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.enabled = (mailSender != null) && enabled && !toEmail.isEmpty() && !fromEmail.isEmpty();
    }

    public void sendContactForm(String name, String email, String message) {
        if (!enabled) {
            System.out.println("[EmailService] Email отправка отключена. Проверьте настройки CONTACT_EMAIL_ENABLED, CONTACT_EMAIL_TO, CONTACT_EMAIL_FROM");
            return;
        }

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Новое сообщение с сайта La Miga от " + name);
            mailMessage.setText(
                    "Новое сообщение с формы обратной связи:\n\n" +
                    "Имя: " + name + "\n" +
                    "Email: " + email + "\n" +
                    "Сообщение:\n" + message + "\n\n" +
                    "---\n" +
                    "Ответить: " + email
            );
            mailSender.send(mailMessage);
            System.out.println("[EmailService] Письмо успешно отправлено на " + toEmail);
        } catch (Exception e) {
            System.err.println("[EmailService] Ошибка отправки письма: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

