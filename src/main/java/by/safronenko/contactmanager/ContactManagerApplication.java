package by.safronenko.contactmanager;

import by.safronenko.contactmanager.config.JpaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"by.safronenko.contactmanager"})
public class ContactManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ContactManagerApplication.class, args);
	}
}
