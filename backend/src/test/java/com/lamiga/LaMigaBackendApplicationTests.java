package com.lamiga;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "contact.email.enabled=false"
})
class LaMigaBackendApplicationTests {

	@Configuration
	static class TestConfig {
		@Bean
		@Primary
		public JavaMailSender javaMailSender() {
			return mock(JavaMailSender.class);
		}
	}

	@Test
	void contextLoads() {
		// Test that the application context loads successfully
	}

}
