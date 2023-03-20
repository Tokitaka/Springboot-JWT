package shop.mtcoding.jwtclone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import shop.mtcoding.jwtclone.model.User;
import shop.mtcoding.jwtclone.model.UserRepository;

@SpringBootApplication
public class JwtcloneApplication {
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository) {
		return (args) -> {
			userRepository
					.save(User.builder().username("ssar").password("1234").email("ssar@nate.com").role("user").build());
			userRepository.save(
					User.builder().username("admin").password("1234").email("admin@nate.com").role("admin").build());
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtcloneApplication.class, args);
	}

}
