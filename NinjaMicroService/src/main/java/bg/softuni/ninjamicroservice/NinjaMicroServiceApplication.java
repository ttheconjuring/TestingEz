package bg.softuni.ninjamicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NinjaMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NinjaMicroServiceApplication.class, args);
	}

}
