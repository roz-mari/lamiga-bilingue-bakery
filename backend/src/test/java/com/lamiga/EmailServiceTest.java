package com.lamiga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmailService Tests")
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    private EmailService emailService;
    private static final String TEST_TO_EMAIL = "test@example.com";
    private static final String TEST_FROM_EMAIL = "noreply@example.com";

    @BeforeEach
    void setUp() {
        // EmailService with email enabled
        emailService = new EmailService(
                mailSender,
                TEST_TO_EMAIL,
                TEST_FROM_EMAIL,
                true
        );
    }

    @Test
    @DisplayName("Should send email when service is enabled")
    void shouldSendEmailWhenServiceIsEnabled() {
        String name = "John Doe";
        String email = "john@example.com";
        String message = "Test message";

        emailService.sendContactForm(name, email, message);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals(TEST_FROM_EMAIL, sentMessage.getFrom());
        assertEquals(TEST_TO_EMAIL, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains(name));
        assertTrue(sentMessage.getText().contains(name));
        assertTrue(sentMessage.getText().contains(email));
        assertTrue(sentMessage.getText().contains(message));
    }

    @Test
    @DisplayName("Should not send email when service is disabled")
    void shouldNotSendEmailWhenServiceIsDisabled() {
        EmailService disabledService = new EmailService(
                mailSender,
                TEST_TO_EMAIL,
                TEST_FROM_EMAIL,
                false
        );

        disabledService.sendContactForm("John Doe", "john@example.com", "Test message");

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Should not send email when mailSender is null")
    void shouldNotSendEmailWhenMailSenderIsNull() {
        EmailService serviceWithoutMailSender = new EmailService(
                null,
                TEST_TO_EMAIL,
                TEST_FROM_EMAIL,
                true
        );

        serviceWithoutMailSender.sendContactForm("John Doe", "john@example.com", "Test message");

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Should not send email when toEmail is empty")
    void shouldNotSendEmailWhenToEmailIsEmpty() {
        EmailService serviceWithoutToEmail = new EmailService(
                mailSender,
                "",
                TEST_FROM_EMAIL,
                true
        );

        serviceWithoutToEmail.sendContactForm("John Doe", "john@example.com", "Test message");

        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Should handle email sending exception gracefully")
    void shouldHandleEmailSendingExceptionGracefully() {
        doThrow(new RuntimeException("SMTP error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Should not throw exception
        assertDoesNotThrow(() -> {
            emailService.sendContactForm("John Doe", "john@example.com", "Test message");
        });

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}

