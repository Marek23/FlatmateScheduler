package pl.com.flat;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.com.flat.repository.*;

@SpringBootApplication
public class FlatApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlatApplication.class, args);
	}

	@Bean
	ApplicationRunner init(
			ResidentRepository   ur,
			RoleRepository       rr,
			StlTypeRepository    str,
			TaskTypeRepository   ttr,
			SettlementRepository sr,
			PaymentRepository    pr) {
		return args -> {
			System.out.println("START");
		};
	};
}

