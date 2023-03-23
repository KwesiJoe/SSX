package io.staxex.api;

import io.staxex.api.authentication.models.Trader;
import io.staxex.api.authentication.repositories.TraderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(TraderRepository traderRepository) {
//		return args -> {
//			traderRepository.save(new Trader("joe", "larbi", "joe@here.com", "somepass"));
//		};
//	}
}
