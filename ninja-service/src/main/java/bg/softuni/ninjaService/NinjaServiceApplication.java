package bg.softuni.ninjaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NinjaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NinjaServiceApplication.class, args);
	}

}
