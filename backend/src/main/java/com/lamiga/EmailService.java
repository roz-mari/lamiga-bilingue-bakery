package com.lamiga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(JavaMailSender.class)
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
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
        this.toEmail = toEmail != null ? toEmail.trim() : "";
        this.fromEmail = fromEmail != null ? fromEmail.trim() : "";
        this.enabled = (mailSender != null) && enabled && !this.toEmail.isEmpty() && !this.fromEmail.isEmpty();
        
        if (this.enabled) {
            log.info("EmailService initialized: emails will be sent to {}", this.toEmail);
        } else {
            log.info("EmailService initialized: email sending is disabled. Configure CONTACT_EMAIL_ENABLED, CONTACT_EMAIL_TO, CONTACT_EMAIL_FROM, and spring.mail.* properties to enable");
        }
    }

    public void sendContactForm(String name, String email, String message) {
        if (!enabled) {
            log.debug("Email sending skipped: service is disabled");
            return;
        }

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("New message from La Miga website from " + name);
            mailMessage.setText(
                    "New message from contact form:\n\n" +
                    "Name: " + name + "\n" +
                    "Email: " + email + "\n" +
                    "Message:\n" + message + "\n\n" +
                    "---\n" +
                    "Reply to: " + email
            );
            mailSender.send(mailMessage);
            log.info("Email successfully sent to {} for contact from {}", toEmail, email);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage(), e);
            // Don't throw exception to avoid breaking file saving
        }
    }
}

