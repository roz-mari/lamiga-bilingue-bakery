package com.lamiga.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.assertj.core.api.Assertions.assertThat;

class MailConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(MailConfig.class);

    @Test
    @DisplayName("JavaMailSender bean is not created when spring.mail.host is absent")
    void shouldNotCreateMailSenderWhenHostMissing() {
        contextRunner.run(context ->
                assertThat(context).doesNotHaveBean(JavaMailSender.class)
        );
    }

    @Test
    @DisplayName("JavaMailSender bean is created when mail properties are provided")
    void shouldCreateMailSenderWhenPropertiesProvided() {
        contextRunner
                .withPropertyValues(
                        "spring.mail.host=smtp.example.com",
                        "spring.mail.port=2525",
                        "spring.mail.username=test-user",
                        "spring.mail.password=test-pass",
                        "spring.mail.properties.mail.smtp.auth=false",
                        "spring.mail.properties.mail.smtp.starttls.enable=false"
                )
                .run(context -> {
                    assertThat(context).hasSingleBean(JavaMailSender.class);

                    JavaMailSenderImpl mailSender = context.getBean(JavaMailSenderImpl.class);
                    assertThat(mailSender.getHost()).isEqualTo("smtp.example.com");
                    assertThat(mailSender.getPort()).isEqualTo(2525);
                    assertThat(mailSender.getUsername()).isEqualTo("test-user");
                    assertThat(mailSender.getPassword()).isEqualTo("test-pass");

                    assertThat(mailSender.getJavaMailProperties())
                            .containsEntry("mail.smtp.auth", "false")
                            .containsEntry("mail.smtp.starttls.enable", "false");
                });
    }
}

